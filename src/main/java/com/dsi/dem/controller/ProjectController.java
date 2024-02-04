package com.dsi.dem.controller;

import com.dsi.dem.model.Employee;
import com.dsi.dem.model.Project;
import com.dsi.dem.service.EmpService;
import com.dsi.dem.service.ProjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProjectController {
    @Autowired
    EmpService empService;
    @Autowired
    ProjService projService;


    @GetMapping("/add_project")
    public String addProject(Model model){
        List<Employee> employeeList = empService.getAll();
        model.addAttribute("employees", employeeList);
        return "addProjectForm";
    }
    @PostMapping("/project")
    public String handleAddProject(@ModelAttribute Project project) {
        Project p = projService.save(project);
        System.out.println(p);
        return "redirect:/projects";

    }

    @GetMapping("/projects")
    public String showProjects(Model model){
        List<Project> projects = projService.getAll();
        model.addAttribute("projects", projects);
        return "projects";
    }

    @GetMapping("/projects/{id}")
    public String showSingleProject(@PathVariable("id") int id , Model model){
        Project p = projService.getById(id);
        model.addAttribute("project",p);
        return "project";
    }
}
