package com.digital3.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class AuthService {
	//Map<Token, <personId, loginTime>>
	private static Map<Long, Map> authMap = new HashMap<>();
	private static Map<Long, Long> loginMap = new HashMap<>();
	
	public boolean isValidToken(Long token) throws Exception{
		long start = loginMap.get(getAuthPersonId(token));
		long currentTime = System.currentTimeMillis();
		long elapse = currentTime - start;
		
		if(start == 0) {
			return false;
		}
		
		if(elapse > 30 * 60 * 1000){ //30*60*1000 = 30분
			System.out.println("timeout");
			authMap.remove(token);
			return false;
		}
		return true;
	}
	
	
	public synchronized Map<Long, Map> getAuthMap() {
		return authMap;
	}

	public synchronized void setAuthMap(Map<Long, Map> authMap) {
		AuthService.authMap = authMap;
	}

	
	public synchronized Map<Long, Long> getLoginMap() {
		return loginMap;
	}

	public synchronized void setLoginMap(Map<Long, Long> loginMap) {
		AuthService.loginMap = loginMap;
	}

	public synchronized Long getAuthPersonId(long token) { //token으로 personId 반환
		Iterator<Long> itr = authMap.get(token).keySet().iterator();
		
		return itr.next();
	}
	
	
}
