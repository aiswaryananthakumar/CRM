package com.techietact.crm.service;

import java.util.List;

import com.techietact.crm.entity.Role;

public interface RoleService {

	    List<Role> getRoles();
	    Role getRole(int id);
	    void saveRole(Role role);
	    Role findByName(String roleName);
		Role getRoleById(int roleId);
		void softDeleteRole(int id);
	    void restoreRole(int id);
	    List<Role> searchRoles(String roleName);
	    Role getRoleByName(String roleName);
	    List<Role> getRoleByLoginId(int loginId);

}
