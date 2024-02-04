package com.dsi.dem.dao;

import com.dsi.dem.model.Employee;
import com.dsi.dem.model.Project;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjRepository extends JpaRepository<Project, Integer> {
}
