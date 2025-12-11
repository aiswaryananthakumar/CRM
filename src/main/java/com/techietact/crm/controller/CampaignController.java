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

import com.techietact.crm.entity.Campaign;
import com.techietact.crm.entity.MyCRMUser;
import com.techietact.crm.service.CampaignService;
import com.techietact.crm.utils.SortUtils;


@Controller
public class CampaignController {

    private CampaignService campaignService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {    //binding the request parameter from the controller
        StringTrimmerEditor trimmer = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, trimmer);
    }

    @GetMapping("/campaign-add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addCampaignPage(Model model) {  // the model works a container that contains the data of the application.
        model.addAttribute("campaign", new Campaign()); 
        return "campaign-form";
    }

    @PostMapping("/campaign-save")
    public String saveCampaign(
            @Valid @ModelAttribute("campaign") Campaign campaign,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) { 
            return "campaign-form";
        }
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyCRMUser mycrm = (MyCRMUser) auth.getPrincipal();
        campaign.setCreatedby(mycrm.getLoginId());

        boolean isNew = (campaign.getId() == 0);
        campaignService.addCampaign(campaign); 

        if (isNew) {
            redirectAttributes.addFlashAttribute("msg", "Campaign added successfully!");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Campaign updated successfully!");
        }

        return "redirect:/campaign-list";
    }

    @GetMapping("/campaign-list")
    public String campaignList(@RequestParam(name = "sortBy", required = false) String sortBy, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyCRMUser mycrm = (MyCRMUser) auth.getPrincipal();
        int loginId = mycrm.getLoginId();

        int sort = (sortBy != null) ? Integer.parseInt(sortBy) : SortUtils.SortByName.getValue();

        List<Campaign> campaigns = campaignService.getCampaignsByUser(sort, loginId);

        model.addAttribute("search", null);
        model.addAttribute("campaigns", campaigns);

        return "campaign-list";
    }


    @GetMapping("/campaign-update")
    public String updateCampaignPage(@RequestParam("id") int id, Model model) {
        model.addAttribute("campaign", campaignService.getCampaign(id)); 
        return "campaign-form";
    }

    @GetMapping("/campaign-delete")
    public String deleteCampaign(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        campaignService.deleteCampaign(id); 
        redirectAttributes.addFlashAttribute("msg", "Campaign deleted successfully!");
        return "redirect:/campaign-list";
    }

    @GetMapping("/campaign-search")
    public String searchCampaign(@RequestParam(required = false) String search, Model model) {
        if (search != null) {
            model.addAttribute("search", search);
            model.addAttribute("campaigns", campaignService.searchCampaign(search));
            return "campaign-list";
        }
        return "redirect:/campaign-list";
    }
    
    @GetMapping("/campaign-softDelete")
    public String softDeleteCampaign(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        campaignService.softDeleteCampaign(id);
        redirectAttributes.addFlashAttribute("msg", "Campaign deleted successfully!");
        return "redirect:/campaign-list";
    }

    @Autowired
    public void setCampaignService(CampaignService campaignService) {
        this.campaignService = campaignService;
    }
}
