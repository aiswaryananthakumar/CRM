package com.techietact.crm.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techietact.crm.entity.Customer;
import com.techietact.crm.entity.MyCRMUser;
import com.techietact.crm.service.CustomerService;
import com.techietact.crm.service.RoleService;
import com.techietact.crm.utils.SortUtils;

@Controller
public class CustomerController {

	@Autowired
    private CustomerService customerService;

    @Autowired
    private RoleService roleService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor trimmer = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, trimmer);
    }

    // Show form
    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addCustomerPage(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("rolesList", roleService.getRoles());
        return "customer-form";
    }
    
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addCustomerPage(Model model,@Valid @ModelAttribute("customer")Customer customer,BindingResult bind,RedirectAttributes redirect) {
        try {
        	if(bind.hasErrors()) {
        		return "customer-form";
        	}
        	customerService.addCustomer(customer);
        	redirect.addFlashAttribute("Success", "Successfully created");
        	return "customer-form";
        }catch(Exception e) {
        	redirect.addFlashAttribute("Error", "sorry!!!");
        }
        return null;
    }

    // Save customer
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveCustomer(
            @Valid @ModelAttribute("customer") Customer customer,
            BindingResult bindingResult,
            RedirectAttributes redirect,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("rolesList", roleService.getRoles());
            return "customer-form";
        }
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyCRMUser mycrm = (MyCRMUser) auth.getPrincipal();
        customer.setCreatedby(mycrm.getLoginId());

        try {
            customerService.addCustomer(customer);
            redirect.addFlashAttribute("Success", "Customer saved successfully!");
        } catch (Exception e) {
            redirect.addFlashAttribute("Error", "Something went wrong!");
        }

        return "redirect:/list";
    }

    @GetMapping("/list")
    public String customerList(@RequestParam(name = "sortBy", required = false) String sortBy, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyCRMUser mycrm = (MyCRMUser) auth.getPrincipal();
        int loginId = mycrm.getLoginId(); // currently logged-in user ID

        List<Customer> customers;
        if (sortBy != null) {
            int sort = Integer.parseInt(sortBy);
            customers = customerService.getCustomersByCreator(loginId, sort);
        } else {
            customers = customerService.getCustomersByCreator(loginId, SortUtils.SortByLastName.getValue());
        }

        model.addAttribute("search", null);
        model.addAttribute("customers", customers);
        return "customer-list";
    }



    @GetMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateCustomerPage(@RequestParam("id") int id, Model model) {
        model.addAttribute("customer", customerService.getCustomer(id));
        model.addAttribute("rolesList", roleService.getRoles());
        return "customer-form";
    }

    @GetMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteCustomer(@RequestParam("id") int id, RedirectAttributes redirect) {
        customerService.deleteCustomer(id);
        redirect.addFlashAttribute("Success", "Customer deleted successfully!");
        return "redirect:/list";
    }

    @GetMapping("/search")
    public String searchCustomer(@RequestParam(required = false) String search, Model model) {
        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("search", search);
            model.addAttribute("customers", customerService.searchCustomer(search));
        } else {
            return "redirect:/list";
        }
        return "customer-list";
    }
    
    @GetMapping("/softDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public String softDeleteCustomer(@RequestParam("id") int id, RedirectAttributes redirect) {
        customerService.softDeleteCustomer(id);
        redirect.addFlashAttribute("Success", "Customer deleted successfully!");
        return "redirect:/list";
    }


    @Autowired
    public void setCustomerDAO(CustomerService customerService) {
        this.customerService = customerService;
    }
}
