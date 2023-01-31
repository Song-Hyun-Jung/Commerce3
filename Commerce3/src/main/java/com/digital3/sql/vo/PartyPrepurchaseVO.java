package com.digital3.sql.vo;

import lombok.Data;

@Data
public class PartyPrepurchaseVO {
	private long orderId;
	private long cartId;
	
	public PartyPrepurchaseVO() {}
	public PartyPrepurchaseVO(long orderId, long cartId) {
		super();
		this.orderId = orderId;
		this.cartId = cartId;
	}
	
	
}
