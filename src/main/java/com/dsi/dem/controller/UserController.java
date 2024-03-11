package com.dsi.dem.controller;

import com.dsi.dem.model.Employee;
import com.dsi.dem.model.Project;
import com.dsi.dem.model.User;
import com.dsi.dem.repository.EmployeeRepository;
import com.dsi.dem.repository.UserRepository;
import com.dsi.dem.service.EmployeeService;
import com.dsi.dem.service.ProjectService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
public class UserController {
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ProjectService projectService;

    public UserController(EmployeeService employeeService, EmployeeRepository employeeRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, ProjectService projectService) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.projectService = projectService;
    }

    @ModelAttribute
    public Employee getEmployee(Principal principal) {
        return employeeRepository.getEmployeeByEmail(principal.getName());
    }
    @ModelAttribute
    public User getUser(Principal principal) {
        return userRepository.getUserByEmail(principal.getName());
    }

    @GetMapping("/user/profile")
    public String userProfile(Model model, Principal principal) {
        Employee employee = (Employee) model.getAttribute("employee");
        model.addAttribute("emp", employee);
        Map<String , Long> jobDuration = employeeService.getEmployeeJobDuration(employee);

        model.addAttribute("emp",employee);
        model.addAttribute("year", jobDuration.get("year"));
        model.addAttribute("month", jobDuration.get("month"));
        return "employee";
    }

    @GetMapping ("/user/my-project")
    public String userProject(Model model) {
        Employee employee = (Employee) model.getAttribute("employee");
        if(employee.getProject() != null) {
            Map<String , Long> projectDurationDetails = projectService.getProjectDurationDetails(employee.getProject());
            model.addAttribute("project",employee.getProject());
            model.addAttribute("duration",projectDurationDetails.get("durationInDays"));
            model.addAttribute("remaining",projectDurationDetails.get("remainingInDays"));

        }

        return "project";

    }

    @PostMapping("/user/change-password")
    public String changePassword(@RequestParam String old_password,
                                 @RequestParam String new_password,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        System.out.println(old_password + "    " + new_password);
        User user = (User) model.getAttribute("user");
        if(passwordEncoder.matches(old_password, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(new_password));
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("success", "Password changed successfully!!");
            return "redirect:/user/profile";

        }
        redirectAttributes.addFlashAttribute("error", "Password didn't match!");
        return "redirect:/user/profile";
    }
}
