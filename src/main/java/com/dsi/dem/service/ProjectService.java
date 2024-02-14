package com.dsi.dem.service;

import com.dsi.dem.dao.ProjectRepository;
import com.dsi.dem.model.Employee;
import com.dsi.dem.model.Project;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class ProjectService{
    ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    public List<Project> getAll() {
        return projectRepository.findAll();
    }


    public void save(Project project){
         projectRepository.save(project);
    }


    public Project getById(int id) {
        Optional<Project> opt = projectRepository.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }
        return null;
    }

    public void addEmployeesToProject(Project project) {
        if(project.getEmployeeList() != null){
            for(Employee e: project.getEmployeeList()){
                e.setProject(project);
                e.setStatus(1);
            }
        }
    }

    public void addMoreEmployeesToProject(Project project, List<Employee> employeeList) {
        if(!employeeList.isEmpty()) {
            for(Employee e : employeeList) {
                project.getEmployeeList().add(e);
                e.setProject(project);
                e.setStatus(1);
            }
        }
    }

    public List<Employee> employeeListAfterRemovingEmployeeFromProject(Project project, int employeeId){
        List<Employee> newEmployeeList = new ArrayList<>();
        for(Employee employee : project.getEmployeeList()){
            if(employee.getId() == employeeId){
                System.out.println(employee.getId() + " " + employee.getFullName());
                employee.setStatus(0);
                employee.setProject(null);
            }else{
                newEmployeeList.add(employee);
            }
        }
        return newEmployeeList;
    }

    public Map<String , Long> getProjectDurationDetails(Project project) {
        Map<String , Long> projectDurationDetails = new HashMap<>();
        LocalDate startDate = project.getStartDate();
        LocalDate deadline = project.getDeadline();
        LocalDate today = LocalDate.now();
        long durationInDays = ChronoUnit.DAYS.between(startDate, deadline);
        long remainingInDays = ChronoUnit.DAYS.between(today, deadline);

        projectDurationDetails.put("durationInDays", durationInDays);
        projectDurationDetails.put("remainingInDays", remainingInDays);

        return projectDurationDetails;

    }


}
