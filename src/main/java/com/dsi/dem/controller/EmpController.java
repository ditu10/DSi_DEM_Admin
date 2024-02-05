package com.dsi.dem.controller;

import com.dsi.dem.model.Employee;
import com.dsi.dem.model.Project;
import com.dsi.dem.service.EmpService;
import com.dsi.dem.service.ProjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmpController {

    @Autowired
    EmpService empService;
    @Autowired
    ProjService projService;
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

    @PostMapping("/removeEmp")
    @ResponseBody
    public String handleRemoveEmpFromProject(@RequestParam("empId") int eid, @RequestParam("projId") int pid){
        Project project = projService.getById(pid);
        List<Employee> newEmpList = new ArrayList<>();

        for(Employee employee : project.getEmployeeList()){
            if(employee.getEmpId() == eid){
                System.out.println(employee.getEmpId() + " " + employee.getFullName());
//                project.getEmployeeList().remove(employee);
                employee.setStatus(0);
                employee.setProject(null);
            }else{
                newEmpList.add(employee);
            }
        }
        project.setEmployeeList(newEmpList);
        projService.save(project);
        return "empId = "+eid;
    }
}
