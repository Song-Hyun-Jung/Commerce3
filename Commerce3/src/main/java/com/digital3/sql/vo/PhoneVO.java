package com.digital3.sql.vo;

import lombok.Data;

@Data
public class PhoneVO {
	private long phoneId;
	private String phoneNumber;
	
	public PhoneVO() {}
	public PhoneVO(long phoneId, String phoneNumber) {
		super();
		this.phoneId = phoneId;
		this.phoneNumber = phoneNumber;
	}
	
	
}
