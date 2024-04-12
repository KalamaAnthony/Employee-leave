package com.example.Success18.Department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {
    @Query(value = "SELECT department.department_Name, department.id FROM Department JOIN ON Employees.employee_id =Department.employee_id", nativeQuery=true)
    List<Department> findByName(String departmentName);

}
