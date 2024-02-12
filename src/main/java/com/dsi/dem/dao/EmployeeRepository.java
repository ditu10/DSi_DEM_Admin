package com.dsi.dem.dao;

import com.dsi.dem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query(value = "select * from employee where id =:n" , nativeQuery = true)
    Employee getEmployeeById(@Param("n") int n);

    @Query(value = "update employee set project_id =:pid , status =:status where id =:eid" , nativeQuery = true)
    Employee updateEmp(@Param("pid") int pid, @Param("status") int status, @Param("eid") int eid);

    List<Employee> getEmployeesByStatusEquals(int status);

}
