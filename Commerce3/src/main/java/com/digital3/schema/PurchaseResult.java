package com.digital3.schema;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;

@ApiModel
public class PurchaseResult {//구매 결과를 보여주기 위한 클래스
	
	long purchaseId;
	long orderId;
	long personId;
	List<Cart> purchaseProductList = new ArrayList<>();
	long totalPrice;
	
	
	
	public long getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(long purchaseId) {
		this.purchaseId = purchaseId;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public List<Cart> getPurchaseProductList() {
		return purchaseProductList;
	}
	public void setPurchaseProductList(List<Cart> purchaseProductList) {
		this.purchaseProductList = purchaseProductList;
	}
	public long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}

	
	
	
	
}
