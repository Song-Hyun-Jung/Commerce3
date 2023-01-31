package com.digital3.sql.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.digital3.sql.vo.AddressVO;

@Mapper
public interface AddressMapper {
	public AddressVO getAddress(String addressDetail);
	public AddressVO getAddressById(long addressId);
	public int insertAddress(AddressVO address);
}
