package com.techietact.crm.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techietact.crm.dao.LoginDao;
import com.techietact.crm.dao.RegisterDAO;
import com.techietact.crm.entity.Customer;
import com.techietact.crm.entity.Login;
import com.techietact.crm.entity.Register;
import com.techietact.crm.entity.Role;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	LoginDao loginDao;
	
    @Autowired
    private RegisterDAO registerDAO;
    
    @Autowired
    RoleService roleService; 

    @Override
    @Transactional
    public void save(Register register) {
        // 1. Save into register table
        registerDAO.save(register);

        // 2. Save into login table
        Login login = new Login();
        login.setUserName(register.getUsername());
        login.setPassword(register.getPassword());

        // Create Customer object for linking (depends on your entity design)
        Customer customer = new Customer();
        customer.setId(register.getId()); // Make sure register.getId() returns saved ID
        login.setCustomer(customer);

        // Assign a default role
        Role defaultRole = roleService.getRoleByName("ROLE_USER"); // or however you define roles
        login.setRole(defaultRole);

        loginDao.save(login);
    }

    @Override
    @Transactional
    public void update(Register register) {
        registerDAO.update(register);
    }

    @Override
    public Register get(int id) {
        return registerDAO.get(id);
    }

    @Override
    @Transactional
    public void softDelete(int id) {
        registerDAO.softDelete(id);
    }

    @Override
    public List<Register> getAll() {
        return registerDAO.getAll();
    }
    
    @Override
    public Register login(String username, String password) {
        return registerDAO.login(username, password);
    }
}
