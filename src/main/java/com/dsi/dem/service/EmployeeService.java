package com.dsi.dem.service;

import com.dsi.dem.dao.EmpRepository;
import com.dsi.dem.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class EmployeeService{

    EmpRepository empRepository;

    public EmployeeService(EmpRepository empRepository){
        this.empRepository = empRepository;
    }

    public Employee save(Employee emp) {
        return empRepository.save(emp);
    }

    public List<Employee> getAll(){
        return empRepository.findAll();
    }


//    @Override
//    public Employee getById(int id) {
//        Optional<Employee> e = empRepository.findById(id);
//        if(e.isPresent()){
//            return e.get();
//        }
//        return null;
//    }


    public Employee getEmployeeById(int id) {
        return empRepository.getEmployeeById(id);
    }


    public Employee updateEmpWhenAddedInAProject(int pid, int status, int eid) {
        return empRepository.updateEmp(pid,status,eid);
    }


    public List<Employee> getAvailableEmp(int status) {
        return empRepository.getEmployeesByStatusEquals(status);
    }

    public Map<String, Long> getEmployeeJobDuration(Employee employee){
        LocalDate today = LocalDate.now();
        LocalDate joiningDate = employee.getJoiningDate();

        Map<String , Long> duration = new HashMap<>();

        long year = ChronoUnit.YEARS.between(joiningDate, today);
        long month = ChronoUnit.MONTHS.between(joiningDate,today);
        month = month%12;

        duration.put("year",year);
        duration.put("month",month);

        return duration;

    }
}
