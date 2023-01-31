package com.digital3.sql.vo;

import java.util.List;

import lombok.Data;

@Data
public class PersonVO {
	private long personId;
	private String personName;
	private String gender;
	private String loginId;
	private String password;
	private List<AddressVO> addressList;
	private List<PhoneVO> phoneList;
	
	public PersonVO() {};
	public PersonVO(long personId, String personName, String gender, String loginId, String password) {
		super();
		this.personId = personId;
		this.personName = personName;
		this.gender = gender;
		this.loginId = loginId;
		this.password = password;
	}
	public PersonVO(long personId, String personName, String gender, String loginId, String password,
			List<AddressVO> addressList, List<PhoneVO> phoneList) {
		super();
		this.personId = personId;
		this.personName = personName;
		this.gender = gender;
		this.loginId = loginId;
		this.password = password;
		this.addressList = addressList;
		this.phoneList = phoneList;
	}

	
	
	
}
