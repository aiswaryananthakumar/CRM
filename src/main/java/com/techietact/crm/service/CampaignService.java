package com.techietact.crm.service;

import com.techietact.crm.entity.Campaign;

import java.util.List;

public interface CampaignService {

    void addCampaign(Campaign campaign);

    Campaign getCampaign(int id);

    List<Campaign> getCampaigns(int sortBy);

    void deleteCampaign(int id);

    List<Campaign> searchCampaign(String searchString);
    
    void softDeleteCampaign(int id);
    
    List<Campaign> getCampaignsByUser(int sortBy, int loginId);


}
