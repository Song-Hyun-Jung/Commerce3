package com.digital3.sql.vo;

import java.util.List;

import com.digital3.schema.Cart;

import lombok.Data;

@Data
public class PrepurchaseVO {
	
	private long orderId;
	private long personId;
	private long addressId;
	private long phoneId;
	private List<Long> cartIdList;
	
	public PrepurchaseVO() {}
	public PrepurchaseVO(long orderId, long personId, long addressId, long phoneId,
			List<Long> cartIdList) {
		super();
		this.orderId = orderId;
		this.personId = personId;
		this.addressId = addressId;
		this.phoneId = phoneId;
		this.cartIdList = cartIdList;
	}
	
}
