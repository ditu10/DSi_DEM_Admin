package com.dsi.dem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.time.LocalDate;
import java.util.List;
//enum status{
//    Running,
//    Finished,
//    Delivered,
//    Maintenance
//}

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proj_id")
    private int projectId;
    @Column(name = "proj_name")
    private String projectName;
    @Column(length = 5000)
    private String description;
    private String Status;
    @Column(name = "start_date")
    private LocalDate startDate;
    private LocalDate deadline;

    @OneToMany(mappedBy = "project" , cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Employee> employeeList;

//    @PrePersist
//    @PreUpdate
//    public void updateEmployeesProjectId() {
//        if (employeeList != null) {
//            for (Employee employee : employeeList) {
//                employee.setProject(this);
//            }
//        }
//    }

    public Project(){
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", description='" + description + '\'' +
                ", Status='" + Status + '\'' +
                ", startDate=" + startDate +
                ", deadline=" + deadline +
                ", employeeList=" + employeeList +
                '}';
    }
}
