package com.techietact.crm.entity;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "access")
public class Access extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accessId;

    @Column(name = "access_name", nullable = false, unique = true)
    private String accessName;
    
    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @ManyToMany(mappedBy = "access", fetch = FetchType.LAZY)
    private List<Privillege> privilleges;

    @Transient
    private List<Integer> privilegeIds;

    public Access() {}

    public Access(String accessName, List<Privillege> privillege) {
        this.accessName = accessName;
        this.privilleges = privillege;
    }

    public int getAccessId() { 
    	return accessId; 
    	}
    public void setAccessId(int accessId) { 
    	this.accessId = accessId; 
    	}

    public String getAccessName() {
    	return accessName;
    	}
    public void setAccessName(String accessName) { 
    	this.accessName = accessName; 
    	}
    
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<Privillege> getPrivillege() {
    	return privilleges;
    	}
    public void setPrivillege(List<Privillege> privillege) { 
    	this.privilleges = privillege;
    	}

    public List<Integer> getPrivillegeIds() { 
    	return privilegeIds;
    	}
    public void setPrivilegeIds(List<Integer> privilegeIds) {
    	this.privilegeIds = privilegeIds;
    	}
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Access)) return false;
        Access access = (Access) o;
        return accessId == access.accessId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessId);
    }

    @Override
    public String toString() {
        return "Access{" +
                "id=" + accessId +
                ", name='" + accessName + '\'' +
                '}';
    }
}
