package com.example.Success18.Employee;



import com.example.Success18.Department.Department;
//import com.example.Success18.EmployeeLeave.Leave;
//import com.example.Success18.Utilities.LeaveTypeEnum;
//import com.example.Success18.EmployeeLeave.LeaveStatusEnum;
//import com.example.Success18.Role.Role;
import com.example.Success18.Utilities.LeaveTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
@Entity
public class Employee {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   //@NotEmpty(message = "first name is required")
   @Column(nullable = false)
    private String firstName;

   //@NotEmpty(message = "Last name is required")
   @Column(nullable = false)
   private String lastName;

   //@NotEmpty(message = "Email is required")
   @Column(nullable = false)
    private String email;

   //@NotEmpty(message = "National ID is required")
   @Column(nullable = false)
    private String nationalId;


//   @NotEmpty(message = "Date of birth is required")
   @Column(nullable = false)
   private LocalDate DOB;

   //@NotEmpty(message = "The gender field is required")
   @Column(nullable = false)
   private String gender;

//   @NotEmpty(message = "Employee number is required")
   @Column(nullable = false)
    private String empNo;


   @Column(nullable = false)
    private String phoneNo;

//   @NotEmpty(message = "KRA is required")
   @Column(nullable = false)
    private String KRA;

   //@NotEmpty(message = "Postal address is required")
   @Column(nullable = false)
    private String postalAddress;

     @Column
    private String physicalAddress;

//   @NotEmpty(message = "Nssf number is required").The annotation can't be used for numbers
   @Column(nullable = false)
    private BigInteger nssf_Number;

//    @NotEmpty(message = "Nhif number is required")
    @Column(nullable = false)
     private BigInteger nhifNumber;

   //@NotEmpty(message = "Next of kin required")
   @Column(nullable = false)
    private String nextOfKin;

//   @NotEmpty(message = "Next of kin number is required"). A number can't start with a 0 in java
   @Column(nullable = false)
    private String nextOfKinNumber;

   @Column(nullable = false)
    private String maritalStatus;

   private String status= "Pending";

   private String isHeadOfDepartment = "false";

   @Column(nullable = false)
   private Long departmentId;




//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "department_id", referencedColumnName = "id")
//    @JsonIgnore
//    @ToString.Exclude
//    private Department department;











//unidirectional One to Many. The mapped by is used to set the name of the parent class the owns the relationship in the other class in a bydirectional relationship
 //@OneToMany(mappedBy = "employee",cascade = CascadeType.ALL)
  //private List<LeaveType> leaveTypes;

//One to One and the owning class is the employee class and is mapped by the role class
// @OneToOne(cascade = CascadeType.ALL)
// @JoinColumn(name = "department_id")
// private Department department;


//@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)//means that all operations on the owning entity will be cascaded to the leave entity
////The fetch lazy implies that the leave entities are loaded only when requested
//@JsonIgnore
//@ToString.Exclude
//private List<Leave> Leaves;

    //Many to Many
//  @ManyToMany
//  @JsonIgnore//ignore this field when converting java to JSON
//  @JoinTable(name = "Employee_Role",
//  joinColumns=@JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "role_id")
//  )
//  private Set<Role> assignedRoles = new HashSet<>(); //The java util (set) prevents the incidence of role duplication

//Operational audit
@JsonProperty(access = JsonProperty.Access.READ_ONLY)
 private String postedBy;

 @JsonProperty(access = JsonProperty.Access.READ_ONLY)
 private String modifiedBy;

 @JsonProperty(access = JsonProperty.Access.READ_ONLY)
 private LocalDateTime postedTime;

 @JsonProperty(access = JsonProperty.Access.READ_ONLY)
 private Character postedFlag = 'N';


 @JsonProperty(access = JsonProperty.Access.READ_ONLY)
 private String deletedBy;

 @JsonProperty(access = JsonProperty.Access.READ_ONLY)
 private Character deletedFlag='N';

 @JsonProperty(access = JsonProperty.Access.READ_ONLY)
 private boolean deleted;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String hrApprovedBy=null;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime hrApprovedOn = null;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Character hrApprovedFlag = 'N';


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String remarks = "-";


//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private String status = LeaveStatusEnum.PENDING.toString();

//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private String leaveType = LeaveTypeEnum.ANNUAL_LEAVE.toString();



}
