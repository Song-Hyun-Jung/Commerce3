package com.digital3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digital3.schema.Phone;
import com.digital3.sql.mapper.PhoneMapper;
import com.digital3.sql.vo.PhoneVO;

@Component
public class PhoneService {
	@Autowired
	PhoneMapper PhoneMapper;	
	
	public Phone phoneSearch(String phoneDetail) throws Exception {
		PhoneVO pv = PhoneMapper.getPhone(phoneDetail);
		Phone phone = new Phone();
		if(pv != null) {
			phone.setPhoneId(pv.getPhoneId());
			phone.setPhoneNumber(pv.getPhoneNumber());
		}
		
		return phone;
	}
	
	public Phone phoneSearchById(long phoneId) throws Exception {
		PhoneVO pv = PhoneMapper.getPhoneById(phoneId);
		Phone phone = new Phone();
		if(pv != null) {
			phone.setPhoneId(pv.getPhoneId());
			phone.setPhoneNumber(pv.getPhoneNumber());
		}
		
		return phone;
	}

}
