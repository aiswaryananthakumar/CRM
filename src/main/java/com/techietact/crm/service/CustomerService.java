package com.techietact.crm.service;

import java.util.List;

import com.techietact.crm.entity.Customer;

/**
 * Created by CryptoSingh1337 on 6/1/2021
 */

public interface CustomerService {

    void addCustomer(Customer customer);
    Customer getCustomer(int id);
    List<Customer> getCustomers(int sortBy);
    void deleteCustomer(int id);
    List<Customer> searchCustomer(String searchString);
    void softDeleteCustomer(int id);
    List<Customer> getCustomersByCreator(int createdBy, int sortBy);


}
