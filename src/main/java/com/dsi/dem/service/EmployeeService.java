package com.dsi.dem.service;

import com.dsi.dem.repository.EmployeeRepository;
import com.dsi.dem.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class EmployeeService{

    EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee emp) {
        return employeeRepository.save(emp);
    }

    public Page<Employee> getAllEmployeeByPage(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        return employeeRepository.findAll(pageable);
    }

    public List<Employee> getAll(){
        return employeeRepository.findAll();
    }


    public Employee getEmployeeById(int id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent()){
            return optionalEmployee.get();
        }
        return null;
    }


    public Employee updateEmpWhenAddedInAProject(int pid, int status, int eid) {
        return employeeRepository.updateEmp(pid,status,eid);
    }


    public List<Employee> getAvailableEmp(int status) {
        return employeeRepository.getEmployeesByStatusEquals(status);
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
