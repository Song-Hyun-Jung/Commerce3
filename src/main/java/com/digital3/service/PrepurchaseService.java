package com.digital3.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.digital3.schema.Address;
import com.digital3.schema.Cart;
import com.digital3.schema.Inventory;
import com.digital3.schema.Phone;
import com.digital3.schema.Prepurchase;
import com.digital3.schema.Product;
import com.digital3.sql.mapper.AddressMapper;
import com.digital3.sql.mapper.PhoneMapper;
import com.digital3.sql.mapper.PrepurchaseMapper;
import com.digital3.sql.vo.AddressVO;
import com.digital3.sql.vo.PartyPrepurchaseVO;
import com.digital3.sql.vo.PhoneVO;
import com.digital3.sql.vo.PrepurchaseVO;

@Component
public class PrepurchaseService {
	@Autowired
	PrepurchaseMapper prepurchaseMapper;
	@Autowired
	AddressMapper addressMapper;
	@Autowired
	PhoneMapper phoneMapper;
	@Resource
	AddressService addressSvc;
	@Resource
	PhoneService phoneSvc;
	@Resource
	CartService cartSvc;
	@Resource
	ProductService productSvc;
	@Resource
	InventoryService inventorySvc;
	
	//personId에 해당하는 전체 가주문서 가져오기
	public List<Prepurchase> getAllPrepurchase(String personId) throws Exception{
		try {
			List<PrepurchaseVO> ppVOList = prepurchaseMapper.getAllPrepurchase(Long.parseLong(personId));
			List<Prepurchase> allPrepurchase = new ArrayList<>();
			List<Cart> getCartList = new ArrayList<>();
			for(PrepurchaseVO ppVO : ppVOList) {
				Prepurchase pp = new Prepurchase();
				pp.setOrderId(ppVO.getOrderId());
				pp.setPersonId(ppVO.getPersonId());
				pp.setAddress(addressSvc.addressSearchById(ppVO.getAddressId()));
				pp.setPhone(phoneSvc.phoneSearchById(ppVO.getPhoneId()));
				for(long cartId : ppVO.getCartIdList()) {
					Cart cart = new Cart();
					cart = cartSvc.getCart(cartId);
					getCartList.add(cart);
				}
				pp.setCartList(getCartList);
				getCartList = new ArrayList<>();
				allPrepurchase.add(pp);
			}
			return allPrepurchase;
			
		}catch(Exception e) {
			throw e;
		}
	}
	
	
	//주문상세 오류 확인
	public boolean checkPrepurchase(Prepurchase prepurchase) throws Exception{
		Map<Long, Long> checkInvenQuantity = new HashMap<Long, Long>(); //카트에 있는 같은 아이템들의 개수가 총 몇개인지 확인
		
		for(Cart cart : prepurchase.getCartList()) {
			Product product = productSvc.productSearchById(cart.getProductId());
			if(product.getProductName() == null){
				throw new Exception("존재하지 않는 상품입니다.");
			}
			if(cart.getProductQuantity() == 0) {
				throw new Exception("수량이 입력되지 않은 상태입니다.");
			}
				
			//주문상세에 있는 같은 아이템들의 개수가 총 몇개인지 확인
			Inventory inventory = inventorySvc.inventorySearchById(""+product.getProductId());
			if(cart.getProductQuantity() > inventory.getQuantity()) {
				throw new Exception("재고가 부족합니다.");
			}
			
			if(checkInvenQuantity.get(cart.getProductId()) == null) {
				checkInvenQuantity.put(cart.getProductId(), cart.getProductQuantity());
			}
			else {
				long curProductQuantity = checkInvenQuantity.get(cart.getProductId()); //현재 주문상세에 담긴 하나의 상품의 개수
				if((curProductQuantity + cart.getProductQuantity()) > inventory.getQuantity()) {
					throw new Exception("재고가 부족합니다.");
				}
				checkInvenQuantity.replace(cart.getProductId(), curProductQuantity + cart.getProductQuantity());
			}
		}
		if(prepurchase.getPhone().getPhoneNumber() == null) {
			throw new Exception("전화번호가 입력되지 않았습니다.");
		}
	
		if(prepurchase.getAddress().getAddressDetail() == null) {
			throw new Exception("주소가 입력되지 않았습니다.");
		}
		return true;
	}

	//가주문서 작성
	@Transactional
	public Prepurchase addPurchaseDetail(Prepurchase prepurchase, String personId) throws Exception{
		prepurchase.setOrderId(System.currentTimeMillis());
		prepurchase.setPersonId(Long.parseLong(personId));
		
		try {	
			if(checkPrepurchase(prepurchase) == true) {	
				//주소
				Address searchAddress = addressSvc.addressSearch(prepurchase.getAddress().getAddressDetail());
					
				if(searchAddress.getAddressDetail() != null) {
					prepurchase.setAddress(searchAddress);
				}
				else { //기존에 저장되어있지 않은 주소의 경우
					AddressVO newAddrVO = new AddressVO(System.currentTimeMillis(), prepurchase.getAddress().getAddressDetail());
					addressMapper.insertAddress(newAddrVO);
					Address newAddr = addressSvc.addressSearch(prepurchase.getAddress().getAddressDetail());
					prepurchase.setAddress(newAddr);
				}
							
				//전화번호
				Phone searchPhone = phoneSvc.phoneSearch(prepurchase.getPhone().getPhoneNumber());
				if(searchPhone.getPhoneNumber() != null) {
					prepurchase.setPhone(searchPhone);
				}
				else { //기존에 저장되어있지 않은 전화번호의 경우
					PhoneVO newPhoneVO = new PhoneVO(System.currentTimeMillis(), prepurchase.getPhone().getPhoneNumber());
					phoneMapper.insertPhone(newPhoneVO);
					Phone newPhone = phoneSvc.phoneSearch(prepurchase.getPhone().getPhoneNumber());
					prepurchase.setPhone(newPhone);
				}
					
				PrepurchaseVO ppVO = new PrepurchaseVO();
				ppVO.setOrderId(prepurchase.getOrderId());
				ppVO.setPersonId(prepurchase.getPersonId());
				ppVO.setAddressId(prepurchase.getAddress().getAddressId());
				ppVO.setPhoneId(prepurchase.getPhone().getPhoneId());
				for(Cart c : prepurchase.getCartList()) {
					PartyPrepurchaseVO pppVO = new PartyPrepurchaseVO(ppVO.getOrderId(), c.getCartId());
					prepurchaseMapper.insertPartyPrepurchase(pppVO);
				}
				
				System.out.println(ppVO.getOrderId());
				prepurchaseMapper.addPurchaseDetail(ppVO);
				
				return getPrepurchase(""+ppVO.getOrderId());
			}
			return null;
		}catch(Exception e) {
				throw e;
		}
	}

	//가주문서 하나 조회
	public Prepurchase getPrepurchase(String prepurchaseId) throws Exception {
		
		try {
			PrepurchaseVO ppVO = prepurchaseMapper.getPrepurchase(Long.parseLong(prepurchaseId));
			Prepurchase pp = new Prepurchase();
			
			List<Cart> getCartList = new ArrayList<>();
			if(ppVO != null) {
				pp.setOrderId(ppVO.getOrderId());
				pp.setPersonId(ppVO.getPersonId());
				pp.setAddress(addressSvc.addressSearchById(ppVO.getAddressId()));
				pp.setPhone(phoneSvc.phoneSearchById(ppVO.getPhoneId()));
				for(long cartId : ppVO.getCartIdList()) {
					Cart cart = new Cart();
					cart = cartSvc.getCart(cartId);
					getCartList.add(cart);
				}
				pp.setCartList(getCartList);
			}
			return pp;
		}catch(Exception e) {
			throw e;
		}
	}
}
