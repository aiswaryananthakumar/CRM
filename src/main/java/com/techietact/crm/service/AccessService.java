package com.techietact.crm.service;

import java.util.List;
import com.techietact.crm.entity.Access;

public interface AccessService {
    List<Access> getAllAccess();
    void saveAccess(Access access);
    Access getAccess(int id);
    void deleteAccess(int id);
    List<Access> findByIds(List<Integer> accessIds);
    Access findByAccessName(String accessName);
    List<Access> findAllByIds(List<Integer> ids);
    List<Access> getAccessByIds(List<Integer> accessIds);
    List<Access> searchAccess(String keyword);
    void softDelete(int id);
    List<Access> getAccessByLoginId(int loginId);


}
