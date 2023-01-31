package com.digital3.sql.vo;

import lombok.Data;

@Data
public class PurchaseVO {
	private long purchaseId;
	private long orderId;
	private long personId;
	
	public PurchaseVO() {}
	public PurchaseVO(long purchaseId, long orderId, long personId) {
		super();
		this.purchaseId = purchaseId;
		this.orderId = orderId;
		this.personId = personId;
	}
	
	
}
