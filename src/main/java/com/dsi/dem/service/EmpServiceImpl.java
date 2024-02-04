package com.dsi.dem.service;

import com.dsi.dem.dao.EmpRepository;
import com.dsi.dem.model.Employee;
import com.dsi.dem.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpServiceImpl implements EmpService{
    @Autowired
    EmpRepository empRepository;
    @Override
    public Employee save(Employee emp) {
        return empRepository.save(emp);
    }

    public List<Employee> getAll(){
        return empRepository.findAll();
    }


    @Override
    public Employee getById(int id) {
        Optional<Employee> e = empRepository.findById(id);
        if(e.isPresent()){
            return e.get();
        }
        return null;
    }

    @Override
    public Employee getEmpById(int id) {
        return empRepository.getEmp(id);
    }
}
