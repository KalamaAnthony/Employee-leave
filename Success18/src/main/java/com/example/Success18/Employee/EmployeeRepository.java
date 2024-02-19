package com.example.Success18.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

@Repository
//bound generic
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    void deleteById (Long id);


}



