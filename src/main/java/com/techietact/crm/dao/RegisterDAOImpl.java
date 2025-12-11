package com.techietact.crm.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import com.techietact.crm.entity.Register;

@Repository
public class RegisterDAOImpl implements RegisterDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Register register) {
        entityManager.persist(register);
    }

    @Override
    @Transactional
    public void update(Register register) {
        entityManager.merge(register);
    }

    @Override
    public Register get(int id) {
        return entityManager.find(Register.class, id);
    }

    @Override
    @Transactional
    public void softDelete(int id) {
        Register register = entityManager.find(Register.class, id);
        if (register != null) {
            // Soft delete using a flag
            // If your entity does not have deleted flag, you should add it
            try {
                entityManager.createQuery("UPDATE Register r SET r.deleted = true WHERE r.id = :id")
                             .setParameter("id", id)
                             .executeUpdate();
            } catch (Exception e) {
                System.out.println("Soft delete not implemented. Entity missing deleted field.");
            }
        }
    }

    @Override
    public List<Register> getAll() {
        return entityManager.createQuery("FROM Register", Register.class).getResultList();
    }

    @Override
    public Register login(String username, String password) {
        try {
            return entityManager.createQuery(
                    "FROM Register r WHERE r.username = :username AND r.password = :password",
                    Register.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (Exception e) {
            // no result or other error -> return null (login failed)
            return null;
        }
    }
}
