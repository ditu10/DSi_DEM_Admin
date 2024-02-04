package com.dsi.dem.service;

import com.dsi.dem.model.Project;

import java.util.List;

public interface ProjService {
    List<Project> getAll();
    Project save(Project project);
    Project getById(int id);
}
