package com.techietact.crm.dao;

import java.util.List;
import com.techietact.crm.entity.Privillege;

public interface PrivillegeDao {
    List<Privillege> getAllPrivilleges();
    void savePrivillege(Privillege privilege);
    Privillege getPrivillege(int id);
    void deletePrivillege(int id);
    Privillege findByName(String privillegeName);
    List<Privillege> findByIds(List<Integer> ids);
    List<Privillege> searchByName(String keyword);
    List<Privillege> getPrivilegesByLoginId(int loginId);
    
}
