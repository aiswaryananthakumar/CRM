package com.techietact.crm.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techietact.crm.dao.EmployeeDAO;
import com.techietact.crm.entity.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;
    

    @Override
    @Transactional
    public void save(Employee employee) {
        List<Employee> existingEmployees = employeeDAO.findByEmployeeName(employee.getFirstName());
        boolean duplicateExists = existingEmployees.stream()
                .anyMatch(e -> e.getLastName().equalsIgnoreCase(employee.getLastName()) &&
                               e.getEmail().equalsIgnoreCase(employee.getEmail()));
        if (!duplicateExists) {
            employeeDAO.save(employee);
        }
    }

    @Override
    @Transactional
    public void update(Employee employee) {
        employeeDAO.update(employee);
    }

    @Override
    @Transactional
    public void softDelete(int id) {
        // Instead of deleting from DB, mark employee as inactive
        Employee employee = employeeDAO.getEmployee(id);
        if (employee != null) {
            employee.setActive(false); // assuming you have an "active" field in Employee entity
            employeeDAO.update(employee);
        }
    }

    @Override
    @Transactional
    public Employee getEmployee(int id) {
        return employeeDAO.getEmployee(id);
    }

    @Override
    @Transactional
    public List<Employee> getEmployees(int sortBy) {
        return employeeDAO.getEmployees(sortBy); // always return all
    }

    @Override
    @Transactional
    public List<Employee> searchEmployee(String searchString) {
        return employeeDAO.findByEmployeeName(searchString); // no filtering
    }
    
    @Override
    @Transactional
    public List<Employee> getEmployeesByCreator(int loginId, int sortBy) {
        return employeeDAO.getEmployeesByCreator(loginId, sortBy);
    }

}
