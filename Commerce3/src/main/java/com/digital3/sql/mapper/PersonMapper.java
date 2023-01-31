package com.digital3.sql.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.digital3.sql.vo.PartyAddressVO;
import com.digital3.sql.vo.PartyPhoneVO;
import com.digital3.sql.vo.PersonVO;

@Mapper
public interface PersonMapper {
	//public List<PersonVO> getPerson(String loginId);
	public PersonVO getPerson(String loginId);
	public void signUp(PersonVO person);
	public void insertPartyAddress(PartyAddressVO pav);
	public void insertPartyPhone(PartyPhoneVO ppv);
}
