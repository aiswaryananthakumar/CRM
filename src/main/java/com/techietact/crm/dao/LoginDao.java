package com.techietact.crm.dao;

import com.techietact.crm.entity.Login;

public interface LoginDao {
	public Login login(Login login);
	Login findByUserName(String userName); 
	public void save(Login login);

}
