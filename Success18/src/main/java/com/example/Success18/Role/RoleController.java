package com.example.Success18.Role;

import com.example.Success18.Utilities.EntityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController( RoleService roleService){

        this.roleService=roleService;
    }
    @PostMapping("/create")
     public EntityResponse createRole(@RequestBody Role role){

        return roleService.createRole(role);
    }
}
