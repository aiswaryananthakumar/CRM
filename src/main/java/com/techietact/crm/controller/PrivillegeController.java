package com.techietact.crm.controller;

import java.util.ArrayList;
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

import com.techietact.crm.entity.Access;
import com.techietact.crm.entity.MyCRMUser;
import com.techietact.crm.entity.Privillege;
import com.techietact.crm.entity.Role;
import com.techietact.crm.service.PrivillegeService;
import com.techietact.crm.service.RoleService;
import com.techietact.crm.service.AccessService;

@Controller
@RequestMapping("/privileges") // keep plural for consistency
public class PrivillegeController {

    @Autowired
    private PrivillegeService privillegeService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AccessService accessService;
    
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor trimmer = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, trimmer);
    }

    @GetMapping("/list")
    public String listPrivileges(@RequestParam(name = "sortBy", required = false) String sortBy , Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyCRMUser mycrm = (MyCRMUser) auth.getPrincipal();

        List<Privillege> privileges = privillegeService.getPrivilegesByLoginId(mycrm.getLoginId());
        model.addAttribute("privileges", privileges);
        return "list-privilege";
    }


    @GetMapping("/showForm")
    public String showForm(Model model) {
        model.addAttribute("privilege", new Privillege()); 
        List<Role> roles = roleService.getRoles();
        for(Role r : roles) {
        	System.out.println(r.getRoleId());
        	System.out.println(r.getRoleName());
        }
        model.addAttribute("roles", roleService.getRoles());
        List<Access> accessList = accessService.getAllAccess();
        for(Access r : accessList) {
        	System.out.println(r.getAccessId());
        	System.out.println(r.getAccessName());
        }
        model.addAttribute("accessList", accessList);
        model.addAttribute("allAccesses", accessService.getAllAccess());
        return "privilege-form";
    }

    @PostMapping("/save")
    public String savePrivillege(@Valid @ModelAttribute("privilege") Privillege privilege,
                                  BindingResult result, Model model) {
    	System.out.println("#########Role"+privilege.getRole().getRoleId());
	       
	       List<Access> accessList = new ArrayList<>();
	       if(privilege.getAccessIds() != null) {
	           accessList = accessService.getAccessByIds(privilege.getAccessIds());
	       }
	       System.out.println("########Access"+accessList);
	       privilege.setAccess(accessList);
        if (result.hasErrors()) {
            model.addAttribute("roles", roleService.getRoles());
            model.addAttribute("allAccess", accessService.getAllAccess());
            return "privilege-form";
        }
        
        List<Access> accessList1 = new ArrayList<>();
        if (privilege.getAccessIds() != null && !privilege.getAccessIds().isEmpty()) {
            accessList1 = accessService.getAccessByIds(privilege.getAccessIds());
        }
        privilege.setAccess(accessList1);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyCRMUser mycrm = (MyCRMUser) auth.getPrincipal();
        privilege.setCreatedby(mycrm.getLoginId());

        Privillege existing = privillegeService.findByName(privilege.getPrivillegeName());
        if (existing != null && (privilege.getPrivillegeId() == 0 || existing.getPrivillegeId() != privilege.getPrivillegeId())) {
            model.addAttribute("errorMessage", "Privilege already exists");
            model.addAttribute("roles", roleService.getRoles());
            model.addAttribute("allAccess", accessService.getAllAccess());
            return "privilege-form";
        }

        privillegeService.savePrivillege(privilege);
        model.addAttribute("successMessage", "Privilege saved successfully!");
        return "redirect:/privileges/list";
    }

    @GetMapping("/updateForm")
    public String updateForm(@RequestParam("privilegeId") int id, Model model)
    {
    	Privillege privilege = privillegeService.getPrivillege(id);
        model.addAttribute("privilege", privillegeService.getPrivillege(id));
   	    List<Role> roles = roleService.getRoles();
	        for(Role r : roles) {
	        	System.out.println(r.getRoleId());
	        	System.out.println(r.getRoleName());
	        }
        model.addAttribute("roles", roleService.getRoles());
        List<Access> accessList = accessService.getAllAccess();
        for(Access r : accessList) {
        	System.out.println(r.getAccessId());
        	System.out.println(r.getAccessName());
        }
        model.addAttribute("accessList", accessList);
        model.addAttribute("allAccess", accessService.getAllAccess());
        return "privilege-form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("privilegeId") int id) {
        privillegeService.deletePrivillege(id);
        return "redirect:/privileges/list";
    }
    
    @GetMapping("/search")
    public String searchPrivilege(@RequestParam("keyword") String keyword, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyCRMUser mycrm = (MyCRMUser) auth.getPrincipal();

        List<Privillege> privileges;
        if (keyword != null && !keyword.trim().isEmpty()) {
            privileges = privillegeService.searchByName(keyword.trim()).stream()
                .filter(p -> p.getCreatedby() == mycrm.getLoginId())
                .toList();
        } else {
            privileges = privillegeService.getPrivilegesByLoginId(mycrm.getLoginId());
        }

        model.addAttribute("privileges", privileges);
        model.addAttribute("keyword", keyword);
        return "list-privilege";
    }


}
