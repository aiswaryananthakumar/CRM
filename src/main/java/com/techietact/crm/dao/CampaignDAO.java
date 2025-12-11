package com.techietact.crm.dao;

import com.techietact.crm.entity.Campaign;

import java.util.List;

public interface CampaignDAO {

    void addCampaign(Campaign campaign);

    Campaign getCampaign(int id);

    List<Campaign> getCampaigns(int sortBy);

    void deleteCampaign(int id);

    List<Campaign> searchCampaign(String searchString);

    Campaign findByCampaignName(String searchString);
    
    void softDeleteCampaign(int id);
    
    List<Campaign> getCampaignsByUser(int sortBy, int loginId);


}
