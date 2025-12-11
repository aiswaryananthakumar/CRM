package com.techietact.crm.dao;

import java.util.List;
import com.techietact.crm.entity.Register;

public interface RegisterDAO {
    void save(Register register);
    void update(Register register);
    Register get(int id);
    void softDelete(int id);
    List<Register> getAll();
    Register login(String username, String password);
}
