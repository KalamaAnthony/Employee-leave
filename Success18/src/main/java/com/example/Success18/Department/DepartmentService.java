package com.example.Success18.Department;

import com.example.Success18.Utilities.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DepartmentService {
    private DepartmentRepo departmentRepo;

    @Autowired
    public DepartmentService(DepartmentRepo departmentRepo) {

        this.departmentRepo = departmentRepo;
    }

    public EntityResponse addDepartment(Department department) {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            Department savedDepartment = departmentRepo.save(department);
            departmentRepo.save(savedDepartment);
            entityResponse.setEntity(savedDepartment);
            entityResponse.setMessage("Your department has been created successfully");
            entityResponse.setStatusCode(HttpStatus.OK.value());

        } catch (Exception e) {
            //log.error();
        }
        return entityResponse;
    }

    public EntityResponse updateDepartmentById(Department updatedDepartment) {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            Optional<Department> existingDepartmentOptional = departmentRepo.findById(updatedDepartment.getId());
            if (existingDepartmentOptional.isPresent()) {
                Department existingDepartment = existingDepartmentOptional.get();
                existingDepartment.getDepartmentName();
                entityResponse.getEntity();
                entityResponse.setMessage("Department has been updated successfully");
                entityResponse.setStatusCode(HttpStatus.OK.value());

                existingDepartment.setDepartmentName("");

            }

        } catch (Exception e) {
            log.error(e.toString());
        }
        return entityResponse;
    }

    public EntityResponse deleteDepartmentById(Long id) {
        EntityResponse entityResponse = new EntityResponse<>();

        try {
            Optional<Department> existingDepartment = departmentRepo.findById(id);
            if (existingDepartment.isPresent()) {
                Department department = existingDepartment.get();
                entityResponse.getEntity();
                entityResponse.setMessage("Department deleted successfully");
                entityResponse.setStatusCode(HttpStatus.OK.value());
                entityResponse.setSuccess(false);

                departmentRepo.save(department);
            }
        } catch (Exception e) {
            //  log.error(e.toString());
            entityResponse.getMessage();

        }
        return entityResponse;
    }

public Optional<Department> getDepartmentById(Long id){
        return departmentRepo.findById(id);
}

public List<Department> getAll(){
        return departmentRepo.findAll();
}
}
