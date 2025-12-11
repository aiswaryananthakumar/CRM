package com.techietact.crm.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techietact.crm.entity.Access;
import com.techietact.crm.entity.MyCRMUser;
import com.techietact.crm.entity.Privillege;
import com.techietact.crm.service.AccessService;
import com.techietact.crm.service.PrivillegeService;

@Controller
@RequestMapping("/access")
public class AccessController {

    @Autowired
    private AccessService accessService;

    @Autowired
    private PrivillegeService privillegeService;

    @GetMapping("/list")
    public String listAccess(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyCRMUser mycrm = (MyCRMUser) auth.getPrincipal();

        List<Access> accessList = accessService.getAccessByLoginId(mycrm.getLoginId());

        model.addAttribute("accessList", accessList);
        return "list-access";
    }




    @GetMapping("/showForm")
    public String showForm(Model model) {
        model.addAttribute("access", new Access());
        model.addAttribute("allPrivilleges", privillegeService.getAllPrivilleges());
        return "access-form";
    }

    @PostMapping("/save")
    public String saveAccess(@Valid @ModelAttribute("access") Access access,
                             BindingResult result, 
                             Model model,
                             RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("allPrivilleges", privillegeService.getAllPrivilleges());
            return "access-form";
        }
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyCRMUser mycrm = (MyCRMUser) auth.getPrincipal();
        access.setCreatedby(mycrm.getLoginId());

        // Check for duplicate access name
        Access existing = accessService.findByAccessName(access.getAccessName());
        if (existing != null && (access.getAccessId() == 0 || existing.getAccessId() != access.getAccessId())) {
            model.addAttribute("allPrivilleges", privillegeService.getAllPrivilleges());
            model.addAttribute("errorMessage", "Access already exists");
            return "access-form";
        }

        // Convert selected IDs into Privillege objects
        if (access.getPrivillegeIds() != null && !access.getPrivillegeIds().isEmpty()) {
            List<Privillege> privileges = privillegeService.findByIds(access.getPrivillegeIds());
            access.setPrivillege(privileges);
        }

        boolean isNew = (access.getAccessId() == 0);
        accessService.saveAccess(access);

        if (isNew) {
            redirectAttributes.addFlashAttribute("msg", "Access added successfully!");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Access updated successfully!");
        }

        return "redirect:/access/list";
    }

    @GetMapping("/updateForm")
    public String updateForm(@RequestParam("accessId") int id, Model model) {
        model.addAttribute("access", accessService.getAccess(id));
        model.addAttribute("allPrivilleges", privillegeService.getAllPrivilleges());
        return "access-form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("accessId") int id, RedirectAttributes redirectAttributes) {
        accessService.deleteAccess(id);
        redirectAttributes.addFlashAttribute("msg", "Access deleted successfully!");
        return "redirect:/access/list";
    }
    
    @GetMapping("/search")
    public String searchAccess(@RequestParam("keyword") String keyword, Model model) {
        List<Access> accessList;
        if (keyword != null && !keyword.trim().isEmpty()) {
            accessList = accessService.searchAccess(keyword);
            model.addAttribute("keyword", keyword);
        } else {
            accessList = accessService.getAllAccess();
        }
        model.addAttribute("accessList", accessList);
        return "list-access";
    }
    
    @GetMapping("/softDelete")
    public String softDeleteAccess(@RequestParam("accessId") int id, RedirectAttributes redirectAttributes) {
        accessService.softDelete(id);
        redirectAttributes.addFlashAttribute("msg", "Access deleted successfully!");
        return "redirect:/access/list";
    }
}
