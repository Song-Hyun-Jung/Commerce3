package com.digital3.sql.vo;

import lombok.Data;

@Data
public class ProductVO {
	private long productId;
	private long categoryId;
	private long price;
	private String productName;
	
	public ProductVO() {}

	public ProductVO(long productId, long categoryId, long price, String productName) {
		super();
		this.productId = productId;
		this.categoryId = categoryId;
		this.price = price;
		this.productName = productName;
	}
	
}
