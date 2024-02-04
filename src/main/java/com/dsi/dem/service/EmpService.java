package com.dsi.dem.service;

import com.dsi.dem.dao.EmpRepository;
import com.dsi.dem.model.Employee;
import com.dsi.dem.model.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface EmpService{
    Employee save(Employee emp);
    List<Employee> getAll();

    Employee getById(int id);

    Employee getEmpById(int id);

    Employee updateEmpWhenAddedInAProject(int pid, int status, int eid);


}
