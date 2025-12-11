package com.techietact.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techietact.crm.dao.LoginDao;
import com.techietact.crm.entity.Login;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	LoginDao loginDao;

	@Override
	public Login login(Login login) {
		Login loginRes=null;//initialize
		 
		//check whether the user name exist in the request object.
		if(null!=login && null!=login.getUserName()) {
		loginRes=loginDao.login(login);
		}
		
		 //The below logic is to compare the user credentials are correct
		 if(null!=loginRes) {
			if(null!=loginRes.getUserName()) {
				if(loginRes.getUserName().equalsIgnoreCase(login.getUserName())) {
					if(loginRes.getPassword().equalsIgnoreCase(login.getPassword())){
						return loginRes;
					}else {
						return null;	
					}
				}
			}
		}
		 return loginRes;
	}

}
