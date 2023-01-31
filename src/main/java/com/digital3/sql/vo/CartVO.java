package com.digital3.sql.vo;

import lombok.Data;

@Data
public class CartVO {
	private long cartId;
	private long personId;
	private long productId;
	private long productQuantity;
	
	public CartVO() {}
	public CartVO(long cartId, long personId, long productId, long productQuantity) {
		super();
		this.cartId = cartId;
		this.personId = personId;
		this.productId = productId;
		this.productQuantity = productQuantity;
	}

}
