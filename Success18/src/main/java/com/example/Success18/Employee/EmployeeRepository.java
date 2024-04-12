package com.example.Success18.Employee;

import com.example.Success18.EmployeeLeave.LeaveStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository <Employee, Long> {
    Optional<Employee> findByEmpNo(Long empNo);

    Optional<Employee> findEmployeeByDepartmentId(Long departmentId);

    Optional<Employee> findEmployeeByIdAndStatus(Long employeeId, LeaveStatusEnum leaveStatusEnum);
}
