package com.example.Success18.EmployeeLeave;

import com.example.Success18.Employee.Employee;
import com.example.Success18.EmployeeLeaveType.LeaveType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.mail.internet.InternetAddress;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "createLeave")
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)

    private String leaveName;
    @Column(nullable = false)
    private String leaveReason;
    @Column(nullable = false)
    private String department;
    @Column(nullable = false)
    private Long leaveType_id; //leave type unique id(autogenerated)
    private Long employee_id; // employee unique id(autogenerated)
    private String hr_reason_for_rejection;
    private String supervisor_reason_for_rejection;

    private Long emp_No;

    private String projectName;
    private Long department_id;
    private  String handover_Note;
    private LocalDate start_Date;
    private LocalDate end_Date;
    private String status = "New";
    private String supervisor_approval = "Pending";
    private String hr_approval = "Pending";
    private Boolean on_leave = false;

    @Column(nullable = false)
    @JsonIgnore
    private Character postedFlag = 'Y';
    @Column(nullable = false)
    @JsonIgnore
    private Date postedTime = new Date();
    @Column(nullable = false, length = 15)
    @JsonIgnore
    private String postedBy = "Employee";
    @Column(length = 15)
    @JsonIgnore
    private String modifiedBy;
    @Column(nullable = false)
    @JsonIgnore
    private Character modifiedFlag = 'N';
    @JsonIgnore
    private Date modifiedTime;
    @Column(nullable = false)
    @JsonIgnore
    private Character deleteFlag = 'N';
    @JsonIgnore
    private Date deleteTime;
    @JsonIgnore
    private String deletedBy;



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

