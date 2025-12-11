package com.techietact.crm.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "employee")
public class Employee extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "First Name is required")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "Mobile is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile must be 10 digits")
    @Column(name = "mobile", unique = true)
    private String mobile;


    @NotBlank(message = "Department is required")
    @Column(name = "department")
    private String department;

    @NotBlank(message = "Designation is required")
    @Column(name = "designation")
    private String designation;

    @NotNull(message = "Salary is required")
    @DecimalMin(value = "1000.0", message = "Salary must be at least 1000")
    @Column(name = "salary")
    private Double salary;

    @NotBlank(message = "Address is required")
    @Column(name = "address")
    private String address;

    @NotBlank(message = "DOB is required")
    @Column(name = "dob")
    private String dob;
    
    @Column(name = "active")
    private boolean active = true; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Role role;


    public Employee() {
    }

    public Employee(String firstName, String lastName, String email, @NotBlank(message = "Mobile is required") @Pattern(regexp = "^[0-9]{10}$", message = "Mobile must be 10 digits") String mobile, String department, String designation, Double salary, String address, String dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.department = department;
        this.designation = designation;
        this.salary = salary;
        this.address = address;
        this.dob = dob;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public @NotBlank(message = "Mobile is required") @Pattern(regexp = "^[0-9]{10}$", message = "Mobile must be 10 digits") String getMobile() { return mobile; }
    public void setMobile(@NotBlank(message = "Mobile is required") @Pattern(regexp = "^[0-9]{10}$", message = "Mobile must be 10 digits") String mobile) { this.mobile = mobile; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }
    
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", mobile=" + mobile +
                ", department='" + department + '\'' +
                ", designation='" + designation + '\'' +
                ", salary=" + salary +
                ", address='" + address + '\'' +
                ", dob='" + dob + '\'' +
                '}';
    }
}
