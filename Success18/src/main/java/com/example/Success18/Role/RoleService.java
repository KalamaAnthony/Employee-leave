package com.example.Success18.Role;

//import com.example.Success18.Employee.Employee;
import com.example.Success18.Utilities.EntityResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Slf4j

public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostMapping("/create")
    public EntityResponse createRole(@RequestBody Role role) {
        EntityResponse entityResponse = new EntityResponse<>();

        try {
            // Perform role creation logic and save to the database
            Role savedRole = roleRepository.save(role);
//            savedRole.setRoleType("CONTRACT");
//            savedRole.setRoleName("OFFICER");
//            savedRole.setPostedFlag('N');
//            savedRole.setPostedBy("SYSTEM");

            roleRepository.save(savedRole);
            entityResponse.setSuccess(true);
            entityResponse.setEntity(savedRole);
            entityResponse.setMessage("Role created successfully.");
            entityResponse.setStatusCode(HttpStatus.CREATED.value());


        } catch (Exception e) {
            log.error(toString(), e);
            entityResponse.setSuccess(false);
            entityResponse.setMessage("Error creating role. Please try again.");
        }

        return entityResponse;
    }}