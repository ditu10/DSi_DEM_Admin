package com.dsi.dem.service;

import com.dsi.dem.dao.ProjRepository;
import com.dsi.dem.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService{
    ProjRepository projRepository;

    public ProjectService(ProjRepository projRepository) {
        this.projRepository = projRepository;
    }


    public List<Project> getAll() {
        return projRepository.findAll();
    }


    public Project save(Project project){
        return projRepository.save(project);
    }


    public Project getById(int id) {
        Optional<Project> opt = projRepository.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }
        return null;
    }
}
