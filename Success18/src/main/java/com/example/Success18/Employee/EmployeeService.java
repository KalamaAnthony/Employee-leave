package com.example.Success18.Employee;

import com.example.Success18.Department.DepartmentRepo;
import com.example.Success18.Utilities.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
@Slf4j
public class EmployeeService {
    @Autowired
     EmployeeRepository employeeRepository;
    @Autowired
      DepartmentRepo departmentRepo;


    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepo departmentRepo) {
        this.employeeRepository = employeeRepository;
        this.departmentRepo = departmentRepo;
    }

    @GetMapping//Logic for fetch all
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }

//methode to find employee By Id
    public Optional<Employee> getEmployeeById(Long id) {

        return employeeRepository.findById(id);
    }


    public EntityResponse createEmployee( Employee employee) {
        EntityResponse entityResponse = new EntityResponse<>();

        try {
            Long count = employeeRepository.count();
            String empNo = "EMP00" + (count + 1);
            employee.setEmpNo(empNo);


            // Save the employee to the database
            Employee savedEmployee = employeeRepository.save(employee);

            savedEmployee.setPostedFlag('Y');
            savedEmployee.setPostedBy("SYSTEM");
            savedEmployee.setPostedTime(LocalDateTime.now());

            // Update the saved employee in the database
            employeeRepository.save(savedEmployee);

            entityResponse.setMessage("Employee created successfully");
            entityResponse.setStatusCode(HttpStatus.CREATED.value());
            entityResponse.setEntity(savedEmployee);

        } catch (Exception e) {
            log.error("Error creating employee!", e);
            entityResponse.setMessage("Failed to create employee");
            entityResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return entityResponse;
    }


    //Add Bulk Employees
    @PostMapping
    public EntityResponse addBulkEmployees(List<Employee> employeeList) {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            List<Employee> existingEmployeeOptional = employeeRepository.saveAll(employeeList);
            entityResponse.setEntity(existingEmployeeOptional);
            entityResponse.setMessage("Employees added successfully");
            entityResponse.setStatusCode(HttpStatus.CREATED.value());

        } catch (Exception e) {
            log.error("error{}", e);

        }
        return entityResponse;

    }


    //build delete employee RESP API
    @DeleteMapping("{id}")
    public EntityResponse deleteEmployeeById(Long id) {
        EntityResponse response = new EntityResponse();
        try {
            Optional<Employee> existingEmployee= employeeRepository.findById(id);

            if (existingEmployee.isPresent()){
                Employee employee=existingEmployee.get();
                employee.setDeletedBy("SYSTEM");
                employee.setDeletedFlag('Y');

               employeeRepository.save(employee);

                response.setMessage("Employee deleted successfully!");
                response.setStatusCode(HttpStatus.OK.value());
                response.setEntity(null);

            }
            // Rest of the code...
        } catch (Exception e) {
            // Exception handling...
        }
        return response;

    }
    @PutMapping("/update")
    public EntityResponse<Employee> updateEmployeeById(@RequestBody Employee updatedEmployee) {
        EntityResponse<Employee> response = new EntityResponse<>();

        try {
            Optional<Employee> existingEmployeeOptional = employeeRepository.findById(updatedEmployee.getId());

            if (existingEmployeeOptional.isPresent()) {
                Employee existingEmployee = existingEmployeeOptional.get();

                // Update the existing employee
                existingEmployee.setFirstName(updatedEmployee.getFirstName());
                existingEmployee.setLastName(updatedEmployee.getLastName());
                existingEmployee.setEmail(updatedEmployee.getEmail());
                existingEmployee.setModifiedBy("SYSTEM");
                existingEmployee.setPostedFlag('Y');
                existingEmployee.setPostedTime(LocalDateTime.now());

                // Save the changes to the existing employee
                employeeRepository.save(existingEmployee);

                response.setMessage("Employee updated successfully");
                response.setStatusCode(HttpStatus.OK.value());
                response.setEntity(existingEmployee);
            } else {
                response.setMessage("Employee not found");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }

        } catch (Exception e) {
            e.printStackTrace(); // Enable this line for debugging
            response.setMessage("Internal server error");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return response;
    }


    public EntityResponse fetchAll() {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            List<Employee> existingEmployee = employeeRepository.findAll();
            if (!existingEmployee.isEmpty()) {
                entityResponse.setEntity(existingEmployee);
                entityResponse.setMessage("Employee retrieved successfully");
                entityResponse.setStatusCode(HttpStatus.OK.value());

            } else {
                entityResponse.setStatusCode(HttpStatus.NO_CONTENT.value());
                entityResponse.setMessage("No Employees available");
                entityResponse.setEntity(null);

            }

        } catch (Exception e) {
            e.printStackTrace();
//            response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
//            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return entityResponse;


    }

    public EntityResponse approveOrReject(List<ChangeOfStatusDTO> changeOfStatusDTOList) {
        //TODO:We use List<ChangeOfStatusDTO> because of the possibilities of existence of more than one employee id in the class

            EntityResponse response = new EntityResponse<>();
            try {
                if (changeOfStatusDTOList.isEmpty()){
                    response.setMessage("You must provide at least one Employee for approval or rejection");
                    response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                }else{
                    //TODO:Declare the new location for the updated employee to a new ArrayList/DEFINE A LIST
                    List<Employee> updatedEmployee = new ArrayList<>();
                    //TODO:Loop through the List
                    for (ChangeOfStatusDTO changeOfStatusDTO :changeOfStatusDTOList){
                        //TODO:Use Optional to check the possibility of occurrence of employee in the list or not
                        Optional<Employee>OptionalEmployee = employeeRepository.findById(changeOfStatusDTO.getId());
                        if (OptionalEmployee.isPresent()){
                            Employee employee = OptionalEmployee.get();
                            String Status = changeOfStatusDTO.getStatus().toUpperCase();
                            //TODO:We can also use switch as an expression and use ->(lambda instead of the break statement)
                            switch (Status){
                                case "APPROVED":
                                    employee.setStatus("APPROVED");
                                    employee.setHrApprovedBy("SYSTEM");
                                    employee.setHrApprovedOn(LocalDateTime.now());
                                    employee.setHrApprovedFlag('Y');
                                    //employee.setRemarks(remarks);
                                    break;
                                case "REJECTED":
                                    employee.setStatus("REJECTED");
                                    employee.setHrApprovedFlag('N');
                                    employee.setHrApprovedBy(null);
                                    employee.setHrApprovedOn(null);
                                    break;
                                case "RETURNED":
                                    employee.setStatus("RETURNED");
                                    break;
                                default:
                                    response.setMessage("Invalid Status provided: "+ Status);
                                    response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                                    return response;

                            }
                            //TODO:Add the updated employee to the new ArrayList
                            updatedEmployee.add(employeeRepository.save(employee));

                        }else {
                            response.setMessage("No Employee found with such an Id: "+changeOfStatusDTO.getId());
                            response.setStatusCode(HttpStatus.NOT_FOUND.value());
                            return response;
                        }

                    }
                    response.setMessage("Employee Status updated Successfully");
                    response.setStatusCode(HttpStatus.OK.value());
                    response.setEntity(updatedEmployee);

                }

            }catch (Exception e){
                log.error(e.toString());
                response.setMessage("An unexpected error occurred while updating status");
                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

            }
            return response;
        }
    }
//    public  EntityResponse approveOrReject(List<Employee> ExistingEmployee, String remarks) {
//        EntityResponse response = new EntityResponse<>();
//        try {
//            if (ExistingEmployee.isEmpty()){
//                response.setMessage("you must provide at least one Employee for approval or rejection");
//                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
//            }else{
//                List<Employee> employees = new ArrayList<>();
//                for (ChangeOfStatusDTO ChangeStatusDTO :ChangeStatusDTOList){
//                    Optional<Employee>OptionalEmployee = employeeRepository.findById(ChangeStatusDTO.getId());
//                    if (OptionalEmployee.isPresent()){
//                        Employee employee = OptionalEmployee.get();
//                        String Status = ChangeStatusDTO.getStatus().toUpperCase();
//                        switch (Status){
//                            case "APPROVED":
//                                employee.setStatus("APPROVED");
//                                employee.setHrApprovedBy("SYSTEM");
//                                employee.setHrApprovedOn(LocalDateTime.now());
//                                employee.setHrApprovedFlag('Y');
//                                employee.setRemarks(remarks);
//                                break;
//                            case "REJECTED":
//                                employee.setStatus("REJECTED");
//                                employee.setHrApprovedFlag('N');
//                                employee.setHrApprovedBy(null);
//                                employee.setHrApprovedOn(null);
//                                break;
//                            default:
//                                response.setMessage("Invalid Status provided: "+ Status);
//                                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
//                                return response;
//
//                        }
//                        employees.add(employeeRepository.save(employee));
//
//                    }else {
//                        response.setMessage("No Employee found with such an Id: "+ChangeStatusDTO.getId());
//                        response.setStatusCode(HttpStatus.NOT_FOUND.value());
//                        return response;
//                    }
//
//                }
//                response.setMessage("Employee Status updated Successfully");
//                response.setStatusCode(HttpStatus.OK.value());
//                response.setEntity(updateEmployee);
//
//            }
//
//        }catch (Exception e){
//            log.error(e.toString());
//            response.setMessage("An unexpected error occurred while updating status");
//            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//
//        }
//        return response;
//    }







