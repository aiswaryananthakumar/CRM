package com.techietact.crm.dao;

import java.util.List;
import com.techietact.crm.entity.Access;

public interface AccessDao {
    List<Access> getAllAccess();
    void saveAccess(Access access);
    Access getAccess(int id);
    void deleteAccess(int id);
    List<Access> findByIds(List<Integer> accessIds);
    Access findByAccessName(String accessName);
    List<Access> findAllByIds(List<Integer> ids);
	List<Access> getAccessByIds(List<Integer> accessId);
	List<Access> searchAccess(String keyword);
	void softDelete(int id);
	List<Access> getAccessByLoginId(int loginId);

}
