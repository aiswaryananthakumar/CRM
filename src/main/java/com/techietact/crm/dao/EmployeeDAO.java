package com.techietact.crm.dao;

import java.util.List;
import com.techietact.crm.entity.Employee;

public interface EmployeeDAO {

    void save(Employee employee);   // handles both add & update

    void update(Employee employee); // explicit update

    Employee getEmployee(int id);

    List<Employee> getEmployees(int sortBy);

    void deleteEmployee(int id);

    List<Employee> searchEmployee(String searchString);

    List<Employee> findByEmployeeName(String searchString);

    long getEmployeeCount();
    
    List<Employee> getEmployeesByCreator(int loginId, int sortBy);

}
