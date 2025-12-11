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

import com.techietact.crm.entity.Lead;
import com.techietact.crm.entity.MyCRMUser;
import com.techietact.crm.service.LeadService;
import com.techietact.crm.utils.SortUtils;

/**
 * Created by CryptoSingh1337 on 6/1/2021
 */

@Controller
public class LeadController {

    private LeadService leadService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor trimmer = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, trimmer);
    }

    @GetMapping("/lead-add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addLeadPage(Model model) {
        model.addAttribute("lead", new Lead()); // add lead attribute so that on submit spring mvc can call setter on it.
        return "lead-form";
    }

    @PostMapping("/lead-save")
    public String saveLead(
            @Valid @ModelAttribute("lead") Lead lead,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "lead-form";
        }
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyCRMUser mycrm = (MyCRMUser) auth.getPrincipal();
        lead.setCreatedby(mycrm.getLoginId());
        
        if (lead.getId() == 0) {
            redirectAttributes.addFlashAttribute("msg", "Lead added successfully!");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Lead updated successfully!");
        }

        leadService.addLead(lead);
        return "redirect:/lead-list";
    }

    @GetMapping("/lead-list")
    public String leadList(@RequestParam(name = "sortBy", required = false) String sortBy, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyCRMUser mycrm = (MyCRMUser) auth.getPrincipal();
        int createdById = mycrm.getLoginId();

        int sort = (sortBy != null) ? Integer.parseInt(sortBy) : SortUtils.SortByLastName.getValue();
        List<Lead> leads = leadService.getLeadsByUser(sort, createdById);

        model.addAttribute("search", null);
        model.addAttribute("leads", leads);
        return "lead-list";
    }


    @GetMapping("/lead-update")
    public String updateLeadPage(@RequestParam("id") int id, Model model) {
        model.addAttribute("lead", leadService.getLead(id)); // add lead attribute with the specific id.
        return "lead-form";
    }

    @GetMapping("/lead-delete")
    public String deleteLead(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        leadService.deleteLead(id);
        redirectAttributes.addFlashAttribute("msg", "Lead deleted successfully!");
        return "redirect:/lead-list";
    }
    
    @GetMapping("/lead-search")
    public String searchLead(@RequestParam(required = false) String search, Model model) {
        if (search != null) {
            model.addAttribute("search", search);
            model.addAttribute("leads", leadService.searchLead(search));
            return "lead-list";
        }
        return "redirect:/lead-list";
    }
    
    @GetMapping("/lead-softDelete")
    public String softDeleteLead(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        leadService.softDeleteLead(id);
        redirectAttributes.addFlashAttribute("msg", "Lead deleted successfully!");
        return "redirect:/lead-list";
    }


    @Autowired
    public void setLeadDAO(LeadService leadService) {
        this.leadService = leadService;
    }
}
