package com.digital3.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.digital3.schema.Cart;
import com.digital3.schema.Inventory;
import com.digital3.schema.Prepurchase;
import com.digital3.schema.Product;
import com.digital3.schema.Purchase;
import com.digital3.schema.PurchaseResult;
import com.digital3.sql.mapper.PurchaseMapper;
import com.digital3.sql.vo.PurchaseVO;

@Component
public class PurchaseService {
	
	@Autowired
	PurchaseMapper purchaseMapper;
	@Resource
	ProductService productSvc;
	@Resource
	PersonService personSvc;
	@Resource
	AddressService addressSvc;
	@Resource
	PhoneService phoneSvc;
	@Resource
	InventoryService inventorySvc;
	@Resource
	CartService cartSvc;
	@Resource
	PrepurchaseService prepurchaseSvc;

	//구매->인벤토리 수량 차감, 데이터베이스 저장
	@Transactional
	public PurchaseResult purchase(Purchase purchase, String personId) throws Exception {
	
		long totalPrice = 0;
		
		long purchaseId = System.currentTimeMillis(); // 구매 id
		long orderId = purchase.getOrderId(); //주문 상세 id
		
		if(orderId == 0) {
			throw new Exception("주문 상세가 입력되지 않았습니다.");
		}
		
		Prepurchase prepurchase = prepurchaseSvc.getPrepurchase(""+orderId); //주문 상세 객체 가져오기
		//System.out.println(prepurchase.getOrderId());
		if(prepurchase.getOrderId() == 0) {
			throw new Exception("주문 상세가 잘못되었습니다.");
		}
		
		PurchaseResult purchaseResult = new PurchaseResult();
		purchaseResult.setPurchaseId(purchaseId);
		purchaseResult.setOrderId(orderId);

		List<Cart> purchaseProductList = prepurchase.getCartList();
		
		//구매수량 인벤토리에서 감소, 총 가격 계산
		for(Cart c : purchaseProductList) {
			Product product = productSvc.productSearchById(c.getProductId());
			Inventory inventory = inventorySvc.inventorySearchById(""+product.getProductId());
			if(inventory.getQuantity() < c.getProductQuantity() || inventory.getQuantity() <= 0) { //주문 상세 이후에 다른사람의 구매로 재고가 부족할수 있음
				throw new Exception("수량 부족. 구매 실패");
			}
			inventorySvc.subtractQuantity(inventory, c.getProductQuantity());
			totalPrice += product.getPrice() * c.getProductQuantity();	
		}
		
		purchaseResult.setPersonId(prepurchase.getPersonId());
		purchaseResult.setPurchaseProductList(purchaseProductList); //카트
		purchaseResult.setTotalPrice(totalPrice);
	
		PurchaseVO pv = new PurchaseVO(purchaseResult.getPurchaseId(), purchaseResult.getOrderId(), purchaseResult.getPersonId());
		purchaseMapper.insertPurchase(pv);
		cartSvc.deleteCart(purchaseProductList); //구매한 상품 장바구니에서 삭제
		
		return purchaseResult;
	}

	
	//주문내역 확인
	public List<Purchase> getPurchaseHistory(String personId) throws Exception{
		try {
			List<PurchaseVO> pVOList = purchaseMapper.getPurchaseHistory(Long.parseLong(personId));
			List<Purchase> histList = new ArrayList<>();
			
			for(PurchaseVO pVO : pVOList) {
				Purchase purchase = new Purchase();
				purchase.setPurchaseId(pVO.getPurchaseId());
				purchase.setPersonId(pVO.getPersonId());
				purchase.setOrderId(pVO.getOrderId());
				histList.add(purchase);
			}

			return histList;
		}catch(Exception e) {
			throw e;
		}
	}
	
}
