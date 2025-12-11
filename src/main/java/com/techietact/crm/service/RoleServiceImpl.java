package com.techietact.crm.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.techietact.crm.dao.RoleDao;

import com.techietact.crm.entity.Role;

@Service
public class RoleServiceImpl implements RoleService{

	    @Autowired
	    private RoleDao roleDAO;

	    @Transactional
	    public List<Role> getRoles() {
	        return roleDAO.findAll();
	    }

	    @Transactional
	    public Role getRole(int id) {
	        return roleDAO.findById(id);
	    }

	    @Transactional
	    public void saveRole(Role role) {
	        roleDAO.save(role);
	    }

	    @Override
	    public void softDeleteRole(int id) {
	        roleDAO.softDelete(id);
	    }

	    @Transactional
		public Role findByName(String roleName) {			
			return roleDAO.findByName(roleName);	
		}
	    
	    @Transactional
	    public Role getRoleById(int id) {
	        return roleDAO.findById(id);
	    }
	    
	    @Override
	    public void restoreRole(int id) {
	        roleDAO.restore(id);
	    }
	    
	    @Override
	    @Transactional
	    public List<Role> searchRoles(String roleName) {
	        return roleDAO.searchRoles(roleName);
	    }
	    
	    @Override
	    public Role getRoleByName(String roleName) {
	        return roleDAO.findByRoleName(roleName);
	    }
	    
	    @Override
	    @Transactional
	    public List<Role> getRoleByLoginId(int loginId) {
	        return roleDAO.getRoleByLoginId(loginId);
	    }


}