package com.digital3.sql.vo;

import lombok.Data;

@Data
public class InventoryVO {
	private long productId;
	private long quantity;
	
	public InventoryVO() {}

	public InventoryVO(long productId, long quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}
	
}
