package com.dsi.dem.controller;

import com.dsi.dem.model.Employee;
import com.dsi.dem.model.User;
import com.dsi.dem.repository.EmployeeRepository;
import com.dsi.dem.repository.UserRepository;
import com.dsi.dem.service.EmployeeService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class UserController {
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(EmployeeService employeeService, EmployeeRepository employeeRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        return "employee";
    }

    @PostMapping("/user/change-password")
    public String changePassword(@RequestParam String old_password,
                                 @RequestParam String new_password,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        System.out.println(old_password + "    " + new_password);
        User user = (User) model.getAttribute("user");
        if(passwordEncoder.matches(user.getPassword(), passwordEncoder.encode(old_password))) {
            user.setPassword(passwordEncoder.encode(new_password));
            userRepository.save(user);
            return "redirect:/user/profile";
        }
        redirectAttributes.addFlashAttribute("error", "Password didn't match!");
        return "redirect:/user/profile";
    }
}
