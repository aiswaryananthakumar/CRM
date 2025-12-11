package com.techietact.crm.dao;

import java.util.List;

import com.techietact.crm.entity.Role;

public interface RoleDao {
	
	    List<Role> findAll();
	    Role findById(int id);
	    void save(Role role);
		Role findByName(String roleName);
		void softDelete(int roleId);
		void restore(int id);
		List<Role> searchRoles(String roleName);
		Role findByRoleName(String roleName);
		List<Role> getRoleByLoginId(int loginId);

		
}