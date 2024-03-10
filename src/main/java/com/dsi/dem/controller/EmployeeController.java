package com.dsi.dem.controller;

import com.dsi.dem.model.Employee;
import com.dsi.dem.model.Project;
import com.dsi.dem.model.User;
import com.dsi.dem.repository.UserRepository;
import com.dsi.dem.security.AppUserDetailsService;
import com.dsi.dem.service.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class EmployeeController {

    // when A class has a constructor with parameters,
    // Spring automatically attempts to resolve the dependencies
    // by matching the types of the parameters with the beans defined in the context
    private final EmployeeService employeeService;
    private final ProjectService projectService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final int pageSize;

    public EmployeeController(EmployeeService employeeService, ProjectService projectService, UserRepository userRepository, UserService userService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
        this.userRepository = userRepository;
        this.userService = userService;

        pageSize = 3;
    }

    @ModelAttribute
    public User getUser(Principal principal) {
        return userRepository.getUserByEmail(principal.getName());
    }

    @GetMapping("/addEmployee")
    public String AddEmployeeForm(){
        return "AddEmployeeForm";
    }

    @PostMapping("/process-employee")
    public String handleAddEmployee(@ModelAttribute Employee employee, @RequestParam String password) {
        User user = userService.addAdditionalInformationForUser(employee,password);
        userRepository.save(user);
//        userRepository.save()
        employeeService.save(employee);
        return "redirect:/admin/employees?page=1";
    }

    @GetMapping("/employees")
    public String showEmployees(@RequestParam int page,
                                Model model){
        Page<Employee> employeeList = employeeService.getAllEmployeeByPage(page-1,pageSize);
        int totalPage = employeeList.getTotalPages();
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("employees", employeeList);
        return "employees";
    }


    @GetMapping("/employees/{id}")
    public String showEmployee(@PathVariable int id, Model model){
        Employee employee = employeeService.getEmployeeById(id);
        Map<String , Long> jobDuration = employeeService.getEmployeeJobDuration(employee);

        model.addAttribute("emp",employee);
        model.addAttribute("year", jobDuration.get("year"));
        model.addAttribute("month", jobDuration.get("month"));
        return "employee";
    }

    @PostMapping("/removeEmployee")
    public String handleRemoveEmployeeFromProject(@RequestParam int employeeId, @RequestParam int projectId){
        Project project = projectService.getById(projectId);

        List<Employee> newEmployeeList = projectService.employeeListAfterRemovingEmployeeFromProject(project,employeeId);
        project.setEmployeeList(newEmployeeList);
        projectService.save(project);
        return "redirect:/admin/projects/"+projectId;
    }

    @GetMapping("/editEmployee/{id}")
    public String editEmployee(@PathVariable int id, Model model){
        Employee emp = employeeService.getEmployeeById(id);
        model.addAttribute("emp", emp);
        return "editEmployeeForm";
    }

    @PostMapping("/editEmployee")
    public String editEmpDetails(@ModelAttribute Employee employee, Model model) {
        Employee emp = employeeService.save(employee);
        model.addAttribute(emp);

        return "redirect:/admin/employees/"+emp.getId();
    }
}
