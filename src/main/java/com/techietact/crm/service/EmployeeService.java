package com.techietact.crm.service;

import java.util.List;
import com.techietact.crm.entity.Employee;

public interface EmployeeService {

    void save(Employee employee);          
    void update(Employee employee);       
    void softDelete(int id);               
    Employee getEmployee(int id);          
    List<Employee> getEmployees(int sortBy); 
    List<Employee> searchEmployee(String searchString); 
    List<Employee> getEmployeesByCreator(int loginId, int sortBy);

}
