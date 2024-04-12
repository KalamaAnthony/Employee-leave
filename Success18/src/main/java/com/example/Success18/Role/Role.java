package com.example.Success18.Role;

//import com.example.Success18.Department.Department;
//import com.example.Success18.Employee.Employee;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
   // private String roleType;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "fk_department" ,referencedColumnName = "id")
//    private Department department;


//    @ManyToOne
//    @JoinColumn(name = "employee_id")
//    private Employee employee;


    //one to one and the mapped class is the role class mapped to the owning class of employees
//    @OneToOne(mappedBy = "role")
//    private Employee employee;


//    @ManyToMany(mappedBy = "assignedRoles")
//    private Set<Employee> employeeSet = new HashSet<>();


//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private String postedBy;
//
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private Character postedFlag = 'N';

}

//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private String role="OFFICER";



//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "roles_employees",
//            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id")
//    )
//    private List<Role> roles = new ArrayList<>();


