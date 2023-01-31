package com.digital3.sql.vo;
import lombok.Data;

@Data
public class AddressVO {
	private long addressId;
	private String addressDetail;
	
	public AddressVO() {};
	public AddressVO(long addressId, String addressDetail) {
		super();
		this.addressId = addressId;
		this.addressDetail = addressDetail;
	}
	
	
}
