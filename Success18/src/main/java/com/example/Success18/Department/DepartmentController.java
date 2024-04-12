package com.example.Success18.Department;

import com.example.Success18.Utilities.EntityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class DepartmentController {
    private DepartmentService departmentService;
    @Autowired
    public DepartmentController(DepartmentService departmentService){

        this.departmentService=departmentService;
    }
    @PostMapping("/add")
    public EntityResponse addDepartment(@RequestBody Department department){
        return departmentService.addDepartment(department);
    }
    @PutMapping("/updateDepartment")
    public EntityResponse updateDepartmentById(@RequestBody Department updatedDepartment){
        return departmentService.updateDepartmentById(updatedDepartment);
    }

    @PutMapping("/delete")
    public EntityResponse deleteDepartmentById(@RequestParam Long id){
        return departmentService.deleteDepartmentById(id);
    }
    @GetMapping
    public Optional<Department> getDepartmentById(@RequestParam Long id){
        return departmentService.getDepartmentById(id);
    }

    public List<Department> getAll(){

        return departmentService.getAll();
    }


}
