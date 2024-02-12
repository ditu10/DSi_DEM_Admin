package com.dsi.dem.controller;

import com.dsi.dem.model.Employee;
import com.dsi.dem.model.Project;
import com.dsi.dem.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {

    // when A class has a constructor with parameters,
    // Spring automatically attempts to resolve the dependencies
    // by matching the types of the parameters with the beans defined in the context
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    public EmployeeController(EmployeeService employeeService, ProjectService projectService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @GetMapping("/addEmployee")
    public String AddEmployeeForm(){
        return "AddEmployeeForm";
    }

    @PostMapping("/employee")
    public String handleAddEmployee(@ModelAttribute Employee employee){
        employeeService.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/employees")
    public String showEmployees(Model model){
        List<Employee> employeeList = employeeService.getAll();
        model.addAttribute("employees", employeeList);
        return "employees";
    }

    @GetMapping("/employee/{id}")
    public String showEmployee(@PathVariable int id, Model model){
        Employee employee = employeeService.getEmployeeById(id);
        Map<String , Long> jobDuration = employeeService.getEmployeeJobDuration(employee);

        model.addAttribute("emp",employee);
        model.addAttribute("year", jobDuration.get("year"));
        model.addAttribute("month", jobDuration.get("month"));
        return "employee";
    }

    @PostMapping("/removeEmp")
    public String handleRemoveEmployeeFromProject(@RequestParam("empId") int eid, @RequestParam("projId") int pid){
        Project project = projectService.getById(pid);
        List<Employee> newEmpList = new ArrayList<>();

        for(Employee employee : project.getEmployeeList()){
            if(employee.getId() == eid){
                System.out.println(employee.getId() + " " + employee.getFullName());
//                project.getEmployeeList().remove(employee);
                employee.setStatus(0);
                employee.setProject(null);
            }else{
                newEmpList.add(employee);
            }
        }
        project.setEmployeeList(newEmpList);
        projectService.save(project);
        return "redirect:/projects/"+pid;
    }

    @GetMapping("/editEmployee/{id}")
    public String editEmployee(@PathVariable int id, Model model){
        Employee emp = employeeService.getEmployeeById(id);

        model.addAttribute("emp", emp);
//        return emp.getFullName();
        return "editEmpForm";
    }

    @PostMapping("/editEmployee")
    public String editEmpDetails(@ModelAttribute Employee employee, Model model){
        Employee emp = employeeService.save(employee);
        System.out.println(emp.getProject());
        model.addAttribute("emp",emp);
        return "redirect:/employee/"+emp.getId();
    }
}
