package com.digital3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digital3.schema.Address;
import com.digital3.sql.mapper.AddressMapper;
import com.digital3.sql.vo.AddressVO;

@Component
public class AddressService {
	@Autowired
	AddressMapper addressMapper;	
	
	public Address addressSearch(String addressDetail) throws Exception {
		AddressVO adv = addressMapper.getAddress(addressDetail);
		Address addr = new Address();
		if(adv != null) {
			addr.setAddressId(adv.getAddressId());
			addr.setAddressDetail(adv.getAddressDetail());
		}
		
		return addr;
	}
	
	public Address addressSearchById(long addressId) throws Exception {
		AddressVO adv = addressMapper.getAddressById(addressId);
		Address addr = new Address();
		if(adv != null) {
			addr.setAddressId(adv.getAddressId());
			addr.setAddressDetail(adv.getAddressDetail());
		}
		
		return addr;
	}
	
}
