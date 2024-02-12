package com.dsi.dem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    @Column(name = "full_name")
    private String fullName;
    private String email;
//    @Column(name = "joining_date")
    private LocalDate joiningDate;
    private String designation;
    private String address;
    private String phone;
    private int status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Project project;

    public Employee() {
    }

    public Employee(int id, String fullName, String email, LocalDate joiningDate, String designation, String address, String phone, int status, Project project) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.joiningDate = joiningDate;
        this.designation = designation;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.project = project;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getId() {
        return id;
    }

    public void setEmpId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

//    @Override
//    public String toString() {
//        return "Employee{" +
//                "empId=" + empId +
//                ", fullName='" + fullName + '\'' +
//                ", email='" + email + '\'' +
//                ", joiningDate=" + joiningDate +
//                ", designation='" + designation + '\'' +
//                ", address='" + address + '\'' +
//                ", phone='" + phone + '\'' +
//                ", status=" + status +
//                ", project=" + project.getProjectName() +
//                '}';
//    }
}
