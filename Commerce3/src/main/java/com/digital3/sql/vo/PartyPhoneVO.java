package com.digital3.sql.vo;

import lombok.Data;

@Data
public class PartyPhoneVO {
	private long personId;
	private long phoneId;
	
	public PartyPhoneVO() {}

	public PartyPhoneVO(long personId, long phoneId) {
		super();
		this.personId = personId;
		this.phoneId = phoneId;
	}
	
}
