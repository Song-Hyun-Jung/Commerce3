package com.digital3.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.digital3.schema.Cart;
import com.digital3.sql.mapper.CartMapper;
import com.digital3.sql.vo.CartVO;


@Component
public class CartService {
	
	@Autowired
	CartMapper cartMapper;
	@Resource
	ProductService productSvc;
	
	//전체 장바구니 가져오기
	public List<Cart> getAllCart(String personId) throws Exception{
			try {
				List<CartVO> cartVOList = cartMapper.getAllCart(Long.parseLong(personId));
				List<Cart> cartList = new ArrayList<Cart>();
			
				for(CartVO cv : cartVOList){
					Cart cart = new Cart();
					cart.setCartId(cv.getCartId());
					cart.setPersonId(cv.getPersonId());
					cart.setProductId(cv.getProductId());
					cart.setProductQuantity(cv.getProductQuantity());
					cartList.add(cart);
				}
			
				return cartList;
			}
			catch(Exception e) {
				throw e;
			}
	}

	//cartId에 해당하는 Cart 찾아오기
	public Cart getCart(long cartId) throws Exception{
		try {
			CartVO cv = cartMapper.getCart(cartId);
			Cart cart = new Cart();
			if(cv != null) {
				cart.setCartId(cv.getCartId());
				cart.setPersonId(cv.getPersonId());
				cart.setProductId(cv.getProductId());
				cart.setProductQuantity(cv.getProductQuantity());
			}
			return cart;
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	//장바구니에 아이템 추가
	public boolean addCart(Cart cart, String personId) throws Exception{ 
		try {
				if(productSvc.productSearchById(cart.getProductId()).getProductName() == null){
					throw new Exception("존재하지 않는 상품입니다.");
				}
				if(cart.getProductQuantity() == 0) {
					throw new Exception("수량이 입력되지 않은 상태입니다.");
				}
				
				long cartId = System.currentTimeMillis();
				cart.setCartId(cartId);
				cart.setPersonId(Long.parseLong(personId));
				
				CartVO cv = new CartVO(cart.getCartId(), cart.getPersonId(), cart.getProductId(), cart.getProductQuantity());
				int insertResult = cartMapper.insertCart(cv);
				
				if(insertResult == 1)
					return true;
				else
					return false;
			} catch (Exception e) {
				throw e;
			}
				
		}
		
		
		//장바구니에서 구매한 아이템 삭제->구매 상태를 'y'로
		@Transactional
		public void deleteCart(List<Cart> purchaseProducts) throws Exception{
			try {
				for(Cart c : purchaseProducts) {
					int result = cartMapper.deleteCart(c.getCartId());
				}
				
			}catch(Exception e) {
				throw e;
			}
		}
		
}
