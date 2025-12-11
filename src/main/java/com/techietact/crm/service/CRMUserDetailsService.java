package com.techietact.crm.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.techietact.crm.dao.LoginDao;
import com.techietact.crm.entity.Access;
import com.techietact.crm.entity.Login;
import com.techietact.crm.entity.MyCRMUser;
import com.techietact.crm.entity.Privillege;
import com.techietact.crm.entity.Role;
import com.techietact.crm.utils.EncryptAndDecrypt;

@Service("CRMUserDetailsService")
@Transactional
public class CRMUserDetailsService implements UserDetailsService{
	
	@Autowired
	LoginDao loginDao;
	
	@Autowired
	LoginService loginService;
	@Autowired
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Override
	public MyCRMUser loadUserByUsername(String username) throws UsernameNotFoundException {
			
		Login loginRes=null;
		MyCRMUser user=null;
		 
		//check whether the user name exist in the request object.
		if(null!=username) {
			Login login=new Login();
			login.setUserName(username);
		    loginRes=loginDao.login(login);
		}
		if(null!=loginRes) {
			 try {
				user= new MyCRMUser(loginRes.getUserName(),getPwd(loginRes.getPassword()),true,true,true,true,getGrantedAuthorities(loginRes.getRole()),loginRes.getUserId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return user;

	}
	
	
	private String getPwd(String pwd) throws Exception {
		String decrypted = EncryptAndDecrypt.decrypt(pwd);
		System.out.println(decrypted);
      	return passwordEncoder().encode(decrypted);
		//return pwd;
		
	}
	
	private List<GrantedAuthority> getGrantedAuthorities(Role role) {
	    List<GrantedAuthority> authorities = new ArrayList<>();

	    if (role == null) return authorities;

	    // Add role as authority
	    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));

	    if (role.getPrivilleges() != null) {
	        for (Privillege privillege : role.getPrivilleges()) {
	            if (privillege.getAccess() != null) {
	                for (Access access : privillege.getAccess()) {
	                    authorities.add(new SimpleGrantedAuthority(access.getAccessName()));
	                }
	            }
	        }
	    }

	    return authorities;
	}


}
