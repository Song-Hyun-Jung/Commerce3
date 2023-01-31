package com.digital3.sql.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.digital3.sql.vo.PhoneVO;

@Mapper
public interface PhoneMapper {
	public PhoneVO getPhone(String phoneNumber);
	public PhoneVO getPhoneById(long phoneId);
	public int insertPhone(PhoneVO phone);
}
