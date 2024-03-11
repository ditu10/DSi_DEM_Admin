package com.dsi.dem.controller;

import com.dsi.dem.model.Employee;
import com.dsi.dem.model.Project;
import com.dsi.dem.model.User;
import com.dsi.dem.repository.UserRepository;
import com.dsi.dem.service.EmployeeService;
import com.dsi.dem.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class ProjectController {

    private final EmployeeService employeeService;
    private final ProjectService projectService;
    private final UserRepository userRepository;

    private final int pageSize;

    public ProjectController(EmployeeService employeeService, ProjectService projectService, UserRepository userRepository) {
        this.employeeService = employeeService;
        this.projectService = projectService;
        this.userRepository = userRepository;
        this.pageSize = 3;
    }

    @ModelAttribute
    public User getUser(Principal principal) {
        return userRepository.getUserByEmail(principal.getName());
    }

    @GetMapping("/addProject")
    public String addProject(Model model) {
        List<Employee> employeeList = employeeService.getAvailableEmp(0);
        model.addAttribute("employees", employeeList);
        return "addProjectForm";
    }

    @PostMapping("/project")
    public String handleAddProject(@ModelAttribute Project project) {
        projectService.addEmployeesToProject(project);
        projectService.save(project);
        return "redirect:/admin/projects?page=1";
    }

    @GetMapping("/projects")
    public String showProjects(@RequestParam int page,
                               Model model) {
        Page<Project> projects = projectService.getAllProjectsByPage(page-1,pageSize);
        int totalPage = projects.getTotalPages();

        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("projects", projects);
        return "projects";
    }

    @GetMapping("/projects/{id}")
    public String showSingleProject(@PathVariable int id,
                                    Model model) {
        Project project = projectService.getById(id);
        Map<String , Long> projectDurationDetails = projectService.getProjectDurationDetails(project);

        model.addAttribute("project",project);
        model.addAttribute("duration",projectDurationDetails.get("durationInDays"));
        model.addAttribute("remaining",projectDurationDetails.get("remainingInDays"));

        return "project";
    }

    @PostMapping("/addEmployeeToProject/{projectId}")
    public String addEmpToProject(@PathVariable int projectId,
                                  @RequestParam String projectName,
                                  Model model) {

        List<Employee> employeeList = employeeService.getAvailableEmp(0);
        model.addAttribute("employees", employeeList);
        model.addAttribute("projectId", projectId);
        model.addAttribute("projectName", projectName);
        return "AddEmployeeToProject";
    }

    @PostMapping("/addEmployeeToProject")
    public String addEmpToProject(@RequestParam("employeeList") List<Employee> employeeList,
                                  @RequestParam("projectId") int projectId,
                                  Model model) {

        Project project = projectService.getById(projectId);
        projectService.addMoreEmployeesToProject(project,employeeList);
        projectService.save(project);

        return "redirect:/admin/projects/"+projectId;
    }
}
