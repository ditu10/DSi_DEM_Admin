package com.dsi.dem.service;

import com.dsi.dem.dao.EmpRepository;
import com.dsi.dem.model.Employee;
import com.dsi.dem.model.Project;

import java.util.List;


public interface EmpService {
    Employee save(Employee emp);
    List<Employee> getAll();
    void updateProject(List<Employee> employees, Project project);

}
