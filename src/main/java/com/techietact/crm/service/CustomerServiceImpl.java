package com.techietact.crm.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.techietact.crm.dao.CustomerDAO;
import com.techietact.crm.entity.Customer;
import com.techietact.crm.entity.Login;
import com.techietact.crm.utils.EncryptAndDecrypt;

/**
 * Created by CryptoSingh1337 on 6/1/2021
 */

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
    private CustomerDAO customerDAO;

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('CUSTOMER_CREATE')")  // check privilege later
    public void addCustomer(Customer customer) {

        // Check if email already exists for a different customer
        List<Customer> existingCustomers = customerDAO.findByCustomerName(customer.getEmail());
        if (!existingCustomers.isEmpty()) {
            for (Customer c : existingCustomers) {
                if (c.getId() != customer.getId()) {
                    throw new RuntimeException("Customer with email '" + customer.getEmail() + "' already exists!");
                }
            }
        }

        if (customer.getId() != 0) {
            // Updating existing customer
            Customer dbCustomer = customerDAO.getCustomer(customer.getId());
            if (dbCustomer != null) {
                dbCustomer.setFirstName(customer.getFirstName());
                dbCustomer.setLastName(customer.getLastName());
                dbCustomer.setEmail(customer.getEmail());
                dbCustomer.setMobile(customer.getMobile());
                dbCustomer.setRole(customer.getRole());

                Login login = dbCustomer.getLogin();
                if (login != null) {
                    login.setUserName(customer.getEmail());
                    login.setRole(customer.getRole());
                }

                customerDAO.addCustomer(dbCustomer); // saveOrUpdate
            }
        } else {
            // New customer → create login
            Login login = new Login();
            login.setUserName(customer.getEmail());
            try {
                login.setPassword(EncryptAndDecrypt.encrypt(customer.getFirstName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            login.setCustomer(customer);
            login.setRole(customer.getRole());
            customer.setLogin(login);

            customerDAO.addCustomer(customer);
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('CUSTOMER_UPDATE')")  // check privilege later
    public Customer getCustomer(int id) {
        return customerDAO.getCustomer(id);
    }

    @Override
    @Transactional
    // ❌ Removed PreAuthorize → so all logged-in users can access list
    public List<Customer> getCustomers(int sortBy) {
        return customerDAO.getCustomers(sortBy);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('CUSTOMER_DELETE')")  // check privilege later
    public void deleteCustomer(int id) {
        customerDAO.deleteCustomer(id);
    }

    @Override
    @Transactional
    // ❌ Removed PreAuthorize → so all logged-in users can search
    public List<Customer> searchCustomer(String searchString) {
        return customerDAO.findByCustomerName(searchString);
    }

    @Autowired
    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }
    
    @Override
    @Transactional
    @PreAuthorize("hasAuthority('CUSTOMER_DELETE')")
    public void softDeleteCustomer(int id) {
        customerDAO.softDeleteCustomer(id);
    }
    
    @Override
    @Transactional
    public List<Customer> getCustomersByCreator(int createdBy, int sortBy) {
        return customerDAO.getCustomersByCreator(createdBy, sortBy);
    }



}
