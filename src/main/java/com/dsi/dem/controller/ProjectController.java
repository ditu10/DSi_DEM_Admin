package com.dsi.dem.controller;

import com.dsi.dem.model.Employee;
import com.dsi.dem.model.Project;
import com.dsi.dem.service.EmpService;
import com.dsi.dem.service.EmployeeService;
import com.dsi.dem.service.ProjService;
import com.dsi.dem.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class ProjectController {

    private final EmployeeService employeeService;
    private final ProjectService projectService;

    public ProjectController(EmployeeService employeeService, ProjectService projectService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @GetMapping("/addProject")
    public String addProject(Model model) {
        List<Employee> employeeList = employeeService.getAvailableEmp(0);
        model.addAttribute("employees", employeeList);
        return "addProjectForm";
    }
    @PostMapping("/project")
    public String handleAddProject(@ModelAttribute("projects") Project project) {
        if(project.getEmployeeList() != null){
            for(Employee e: project.getEmployeeList()){
                e.setProject(project);
                e.setStatus(1);
            }
        }

        Project p = projectService.save(project);

        return "redirect:/projects";

    }

    @GetMapping("/projects")

    public String showProjects(Model model) {
        List<Project> projects = projectService.getAll();
        model.addAttribute("projects", projects);
        return "projects";
    }

    @GetMapping("/projects/{id}")
    public String showSingleProject(@PathVariable("id") int id , Model model) {
        Project p = projectService.getById(id);
        model.addAttribute("project",p);
        LocalDate t1 = p.getStartDate();
        LocalDate t2 = p.getDeadline();
        LocalDate today = LocalDate.now();
        long daysDifference = ChronoUnit.DAYS.between(t1, t2);
        long remaining = ChronoUnit.DAYS.between(today, t2);
        model.addAttribute("duration",daysDifference);
        model.addAttribute("remaining",remaining);

        return "project";
    }

    @PostMapping("/addEmployeeToProject")
    public String addEmpToProject(@RequestParam("projectId") int projId,@RequestParam("projectName") String projName, Model model){
        List<Employee> employeeList = employeeService.getAvailableEmp(0);
        model.addAttribute("employees", employeeList);
        model.addAttribute("proj_id", projId);
        model.addAttribute("proj_name", projName);
        return "AddEmpToProj";
    }

    @PostMapping("/addEmpToProject")
    public String addEmpToProject(@RequestParam("employeeList") List<Employee> employeeList,
                                  @RequestParam("projectId") int projectId,
                                  Model model) {
        Project project = projectService.getById(projectId);

        if(!employeeList.isEmpty()) {
            for(Employee e : employeeList) {
                project.getEmployeeList().add(e);
                e.setProject(project);
                e.setStatus(1);
            }
        }

        projectService.save(project);
        return "redirect:projects/"+projectId;
    }
}
