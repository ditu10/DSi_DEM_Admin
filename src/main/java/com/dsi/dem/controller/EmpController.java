package com.dsi.dem.controller;

import com.dsi.dem.model.Employee;
import com.dsi.dem.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class EmpController {

    @Autowired
    EmpService empService;
    @GetMapping("/add_employee")
    public String AddEmpForm(){
        return "AddEmpForm";
    }

    @PostMapping("/employee")
    public String handleAddEmp(@ModelAttribute Employee employee, Model model){
        System.out.println(employee);
        Employee e = empService.save(employee);
        System.out.println(e);
        return "redirect:/employees";
    }

    @GetMapping("/employees")
    public String showEmployees(Model model){
        List<Employee> employeeList = empService.getAll();
        System.out.println(employeeList);
        model.addAttribute("employees", employeeList);
        return "employees";
    }

    @GetMapping("/employee/{id}")
    public String showEmployee(@PathVariable("id") int id, Model model){
        Employee e = empService.getEmpById(id);
        LocalDate today = LocalDate.now();
        LocalDate t = e.getJoiningDate();

        long year = ChronoUnit.YEARS.between(t, today);
        long month = ChronoUnit.MONTHS.between(t,today);

        month = month%12;

        model.addAttribute("emp",e);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        return "employee";
    }
}
