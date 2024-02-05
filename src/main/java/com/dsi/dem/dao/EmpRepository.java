package com.dsi.dem.dao;

import com.dsi.dem.model.Employee;
import com.dsi.dem.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmpRepository extends JpaRepository<Employee, Integer> {
    @Query(value = "select * from employee where emp_id =:n" , nativeQuery = true)
    Employee getEmp(@Param("n") int n);

    @Query(value = "update employee set project_proj_id =:pid , status =:status where emp_id =:eid" , nativeQuery = true)
    Employee updateEmp(@Param("pid") int pid, @Param("status") int status, @Param("eid") int eid);

    List<Employee> getEmployeesByStatusEquals(int status);

}
