package com.digital3.sql.vo;

import lombok.Data;

@Data
public class PartyAddressVO {
	private long personId;
	private long addressId;
	
	public PartyAddressVO() {}
	public PartyAddressVO(long personId, long addressId) {
		super();
		this.personId = personId;
		this.addressId = addressId;
	}
	
	
}
