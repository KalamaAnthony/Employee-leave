package com.example.Success18.EmployeeLeave;

import com.example.Success18.Employee.Employee;
import com.example.Success18.EmployeeLeaveType.LeaveType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "createLeave")
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String leaveName;
    private String leaveReason;


//    @OneToMany(mappedBy = "leave")
//
//    private List<LeaveType>leaveTypes;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    private Employee employee;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "leave_type_fk", referencedColumnName = "id")
@JsonIgnore
@ToString.Exclude
private LeaveType leaveType;


//To mean one employee can have many leaves
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_fk", referencedColumnName = "id")
    @JsonIgnore
    @ToString.Exclude
    private Employee employee;
    // other attributes, constructors, getters and setters

}

