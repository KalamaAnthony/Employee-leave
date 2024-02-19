package com.example.Success18.Employee;

import com.example.Success18.Utilities.EntityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;//injection of service class so that the controller class can use the services

    @Autowired
    public EmployeeController(EmployeeService employeeService)//initialises the Controller class with an instance of employeeService
    {
        this.employeeService = employeeService;

    }

    @PostMapping("/create")
    public EntityResponse createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);

    }

    @PostMapping("/addbulk")
    public EntityResponse addBulk(@RequestBody java.util.List<Employee> EmployeeList) {
        return employeeService.addBulkEmployees(EmployeeList);
    }


    @GetMapping("/id")
    public Employee getEmployeeById(@RequestParam Long id) {
        return employeeService.getEmployeeById(id)
                .orElseThrow(() -> new RuntimeException("Employee with id " + id + "is not found"));
    }
    @DeleteMapping("/delete")
    public EntityResponse deleteEmployeeById(@RequestParam Long id){
        return employeeService.deleteEmployeeById(id);
    }

    @PutMapping("/update")

    public EntityResponse<Employee> updateEmployee(@RequestBody Employee updatedEmployee) {

        return employeeService.updateEmployeeById(updatedEmployee);

    }

    @GetMapping("/getAll")
    public EntityResponse fetchAll() {
        return employeeService.fetchAll();

    }

    @PutMapping("/approveOrReject")
    public EntityResponse approveOrReject(@RequestBody List<ChangeOfStatusDTO> changeOfStatusDTOList, @RequestParam String remarks) {
        return employeeService.approveOrReject(changeOfStatusDTOList, remarks);

    }
}

//    @GetMapping("/fetchByStatus")
//    public EntityResponse fetchByStatus(@RequestParam("status") String status) {
//        return employeeService.fetchByStatus(status);

//    @PutMapping("/update/state")
//    public EntityResponse updateState(@RequestBody Employee changeStateDTOList) {
//        return employeeService.updateEmployee(changeStateDTOList);
//    }}
//    @PutMapping("/update/{id}")
//    public EntityResponse<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee){
//        return employeeService.updateCustomer(id, updatedEmployee);



