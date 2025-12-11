package com.techietact.crm.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techietact.crm.entity.MyCRMUser;
import com.techietact.crm.entity.Role;
import com.techietact.crm.service.RoleService;

@Controller
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public String listRole(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyCRMUser mycrm = (MyCRMUser) auth.getPrincipal();

        // This ensures your JSP gets the right attribute name
        model.addAttribute("roles", roleService.getRoleByLoginId(mycrm.getLoginId()));

        return "list-role";
    }


    @GetMapping("/showForm")
    public String showForm(Model model) {
        model.addAttribute("role", new Role());
        return "role-form";
    }

    @PostMapping("/save")
    public String saveRole(@ModelAttribute("role") Role role, RedirectAttributes redirectAttributes) {
        Role existingRole = roleService.findByName(role.getRoleName());
        if (existingRole != null && (role.getRoleId() == 0 || existingRole.getRoleId() != role.getRoleId())) {
            return "role-form";
        }
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyCRMUser mycrm = (MyCRMUser) auth.getPrincipal();
        role.setCreatedby(mycrm.getLoginId());
        role.setIsdelete(false);
        roleService.saveRole(role);
        
        if (role.getRoleId() == 0) {
            redirectAttributes.addFlashAttribute("msg", "Role added successfully!");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Role updated successfully!");
        }

        return "redirect:/roles/list";
    }

    @GetMapping("/updateForm")
    public String updateForm(@RequestParam("roleId") int id, Model model) {
        model.addAttribute("role", roleService.getRole(id));
        return "role-form";
    }

    @GetMapping("/delete/{id}")
    public String softDeleteRole(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        roleService.softDeleteRole(id);
        redirectAttributes.addFlashAttribute("msg", "Role deleted successfully!!");
        return "redirect:/roles/list";
    }
    
    @GetMapping("/restore/{id}")
    public String restoreRole(@RequestParam("roleId") int id, RedirectAttributes redirectAttributes) {
        roleService.restoreRole(id);
        redirectAttributes.addFlashAttribute("msg", "Role restored successfully!");
        return "redirect:/roles/list";
    }
    
    @GetMapping("/search")
    public String searchRoles(@RequestParam("keyword") String keyword, Model model) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            List<Role> roles = roleService.searchRoles(keyword);
            model.addAttribute("roles", roles);
        } else {
            model.addAttribute("roles", roleService.getRoles());
        }
        return "list-role";
    }

}
