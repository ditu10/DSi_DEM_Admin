package com.dsi.dem.service;

import com.dsi.dem.dao.EmpRepository;
import com.dsi.dem.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
