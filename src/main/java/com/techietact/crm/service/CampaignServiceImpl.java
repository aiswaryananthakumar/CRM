package com.techietact.crm.service;

import com.techietact.crm.dao.CampaignDAO;
import com.techietact.crm.entity.Campaign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CampaignServiceImpl implements CampaignService {

	@Autowired
    private CampaignDAO campaignDAO;

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('CAMPAIGN_CREATE')")
    public void addCampaign(Campaign campaign) {
        campaignDAO.addCampaign(campaign);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('CAMPAIGN_UPDATE')")
    public Campaign getCampaign(int id) {
        return campaignDAO.getCampaign(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('CAMPAIGN_VIEW')")
    public List<Campaign> getCampaigns(int sortBy) {
        return campaignDAO.getCampaigns(sortBy);
    }

    @Override
    @Transactional      //metadata that specifies that an interface, class, or method must have transactional semantics
    @PreAuthorize("hasAuthority('CAMPAIGN_DELETE')")
    public void deleteCampaign(int id) {
        campaignDAO.deleteCampaign(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('CAMPAIGN_VIEW')")
    public List<Campaign> searchCampaign(String searchString) {
        return campaignDAO.searchCampaign(searchString);
    }

    @Autowired
    public void setCampaignDAO(CampaignDAO campaignDAO) {
        this.campaignDAO = campaignDAO;
    }
    
    @Override
    @Transactional
    @PreAuthorize("hasAuthority('CAMPAIGN_DELETE')")
    public void softDeleteCampaign(int id) {
        campaignDAO.softDeleteCampaign(id);
    }
    
    @Override
    @Transactional
    @PreAuthorize("hasAuthority('CAMPAIGN_VIEW')")
    public List<Campaign> getCampaignsByUser(int sortBy, int loginId) {
        return campaignDAO.getCampaignsByUser(sortBy, loginId);
    }


}
