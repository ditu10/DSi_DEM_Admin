package com.dsi.dem.service;

import com.dsi.dem.dao.ProjRepository;
import com.dsi.dem.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjServiceImpl implements ProjService{
    @Autowired
    ProjRepository projRepository;
    @Override
    public List<Project> getAll() {
        return projRepository.findAll();
    }

    @Override
    public Project save(Project project){
        return projRepository.save(project);
    }

    @Override
    public Project getById(int id) {
        Optional<Project> opt = projRepository.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }
        return null;
    }
}
