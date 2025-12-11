package com.techietact.crm.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techietact.crm.entity.Employee;
import com.techietact.crm.entity.MyCRMUser;
import com.techietact.crm.service.EmployeeService;
import com.techietact.crm.service.RoleService;
import com.techietact.crm.utils.SortUtils;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RoleService roleService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor trimmer = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, trimmer);
    }

    // Add page
    @GetMapping("/add")
    public String addEmployeePage(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("rolesList", roleService.getRoles());
        return "employee-form";
    }
    
    @GetMapping("/home")
    public String employeeHome() {
        return "redirect:/employee/list";
    }

    // Save Employee
    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("rolesList", roleService.getRoles());
            return "employee-form";
        }
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyCRMUser mycrm = (MyCRMUser) auth.getPrincipal();
        employee.setCreatedby(mycrm.getLoginId());

        employeeService.save(employee);
        redirectAttributes.addFlashAttribute("msg", "✅ Employee added successfully!");
        return "redirect:/employee/list";
    }

    // List Employees
    @GetMapping("/list")
    public String employeeList(@RequestParam(name = "sortBy", required = false) String sortBy, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyCRMUser mycrm = (MyCRMUser) auth.getPrincipal();

        int sortValue = sortBy != null ? Integer.parseInt(sortBy) : SortUtils.SortByLastName.getValue();

        List<Employee> employees = employeeService.getEmployeesByCreator(mycrm.getLoginId(), sortValue);

        model.addAttribute("employees", employees);
        return "employee-list";
    }


    // Update Page
    @GetMapping("/update")
    public String updateEmployeePage(@RequestParam("id") int id, Model model) {
        model.addAttribute("employee", employeeService.getEmployee(id));
        model.addAttribute("rolesList", roleService.getRoles());
        return "employee-form";
    }

    // Update Action
    @PostMapping("/update")
    public String updateEmployee(@Valid @ModelAttribute("employee") Employee employee,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("rolesList", roleService.getRoles());
            return "employee-form";
        }

        employeeService.update(employee);
        redirectAttributes.addFlashAttribute("msg", "✅ Employee updated successfully!");
        return "redirect:/employee/list";
    }

    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam("id") int id,
                                 RedirectAttributes redirectAttributes) {
        employeeService.softDelete(id);

        redirectAttributes.addFlashAttribute("msg", "Employee deleted successfully!");
        return "redirect:/employee/list";
    }

    // Search
    @GetMapping("/search")
    public String searchEmployee(@RequestParam(required = false) String search, Model model) {
        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("employees", employeeService.searchEmployee(search));
        } else {
            return "redirect:/employee/list";
        }
        return "employee-list";
    }
}
