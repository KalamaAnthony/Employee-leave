package com.example.Success18.Department;

//import com.example.Success18.Role.Role;
import com.example.Success18.Employee.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String departmentName;
    private String departmentCode;
    private String description;


//    @OneToMany(mappedBy = "Department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    @ToString.Exclude
//    private List<Department> departments;




//    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
//    @Column(name = "employee_id")
//    private Employee employee;

}
