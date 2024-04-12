package com.example.Success18.EmployeeLeave;


import com.example.Success18.Department.Department;
import com.example.Success18.Department.DepartmentRepo;
import com.example.Success18.Employee.Employee;
import com.example.Success18.Employee.EmployeeRepository;
import com.example.Success18.EmployeeLeaveType.LeaveType;
import com.example.Success18.EmployeeLeaveType.LeaveTypeRepository;
import com.example.Success18.Utilities.EntityResponse;
import com.sun.xml.bind.v2.TODO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LeaveService {



@Autowired
  LeaveRepository leaveRepository;
@Autowired
 DepartmentRepo departmentRepo;
@Autowired
 LeaveTypeRepository leaveTypeRepository;

@Autowired
 EmployeeRepository employeeRepository;
//@Autowired
//private JavaFileManager javaFileManager;
   @Autowired
   public LeaveService(LeaveRepository leaveRepository, DepartmentRepo departmentRepo, LeaveTypeRepository leaveTypeRepository, EmployeeRepository employeeRepository){

       this. leaveRepository = leaveRepository;
       this.departmentRepo = departmentRepo;
       this.leaveTypeRepository = leaveTypeRepository;
       this.employeeRepository = employeeRepository;
   }



   //@Value("${mail.host}")
   //private String sendFrom;
    public EntityResponse addLeave(Leave leave) {
       EntityResponse entityResponse = new EntityResponse<>();
       try {

       //Check if employee is active
        Optional<Employee> checkActiveEmployee=employeeRepository.findByEmpNo(leave.getEmp_No());
        if(checkActiveEmployee.isPresent()){
            Employee existingEmployee=checkActiveEmployee.get();

            //Check if the leave type exists.
            Optional<LeaveType> leaveTypeOptional=leaveTypeRepository.findById(leave.getLeaveType_id());
            if(leaveTypeOptional.isPresent()) {
                LeaveType leaveType = leaveTypeOptional.get();

                //Check if handover to employee belong to the same department as employee
               // boolean handOverTo = true;
                Optional<Employee> handOverToEmployee= employeeRepository.findEmployeeByDepartmentId(leave.getDepartment_id());
                if(handOverToEmployee.isPresent()) {
                    handOverToEmployee.get();

                    //Check if employee has new leave
                    Optional<Employee> hasNewLeave = employeeRepository.findEmployeeByIdAndStatus(leave.getEmployee_id(), LeaveStatusEnum.NEW);
                    if(hasNewLeave.isPresent()){
                        entityResponse.setMessage(" You already have an existing leave request leave");
                        entityResponse.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                        entityResponse.setEntity("");
                    }else {
                        // Check if has a leave in processing stage
                        Optional<Employee> hasLeaveInProgress = employeeRepository.findEmployeeByIdAndStatus(leave.getEmployee_id(),LeaveStatusEnum.PROCESSING);
                        if(hasLeaveInProgress.isPresent()){
                            entityResponse.setMessage("You already have a leave in progress");
                            entityResponse.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                            entityResponse.setEntity("");
                        }else{
                            //Check employee has an approved leave
                            Optional<Employee> checkApproved = employeeRepository.findEmployeeByIdAndStatus(leave.getEmployee_id(), LeaveStatusEnum.APPROVED);
                            if ((checkApproved.isPresent())){
                                entityResponse.setMessage("Your leave is already approved, please proceed for leave");
                                entityResponse.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                                entityResponse.setEntity("");
                            }


                        }
                    }


                    leave.setEmployee_id(existingEmployee.getId());
                    leave.setLeaveType(leaveType);
                    leave.setEmployee(existingEmployee);
                    leave.setStatus("NEW");


                    Leave savedLeave=leaveRepository.save(leave);

//                    String mailMessage = "<p>Dear <strong>" + employee.getFirstName() + "</strong>,</p>\n" +
//                            " Your leave request  has been submitted successfully." +
//                            " Contact your supervisor for approval of the request before proceeding";
//
//                    String subject = "Leave Request";
//
//                    mailService.sendEmail(employee.getEmail(), null, mailMessage, subject, false, null, null);

                    entityResponse.setEntity(savedLeave);
                    entityResponse.setMessage("Request for leave has been added successfully");
                    entityResponse.setStatusCode(HttpStatus.OK.value());

                    leaveRepository.save(savedLeave);



                }
            }

        } else {
            entityResponse.setMessage("There was an error creating a leave request");
            entityResponse.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
            entityResponse.setEntity("");
        }

       }catch (Exception e){
       //error handling
   }
   return entityResponse;
   }



   public EntityResponse updateLeaveById(Leave updatedLeave){
       EntityResponse entityResponse=new EntityResponse<>();
   try {
       Optional<Leave> existingLeaveOptional=leaveRepository.findById(updatedLeave.getId());
       if(existingLeaveOptional.isPresent()){
           Leave existingLeave=existingLeaveOptional.get();
           existingLeave.setLeaveName(updatedLeave.getLeaveName());
           //existingLeave.se
           entityResponse.setEntity(updatedLeave);
           existingLeave.setLeaveReason("SYSTEM");
           entityResponse.setMessage("leave updated successfully");
           entityResponse.setStatusCode(HttpStatus.OK.value());
           leaveRepository.save(updatedLeave);
       }

   }catch (Exception e){
       //error handling
   }
   return entityResponse;
   }

   public EntityResponse deleteLeaveById(@RequestParam Long id) {
       EntityResponse entityResponse = new EntityResponse<>();

       try {
           Optional<Leave> existingLeaveOptional = leaveRepository.findById(id);
           if (existingLeaveOptional.isPresent()) {

               entityResponse.setMessage("The leave has been deleted successfully");
               entityResponse.getEntity();
               entityResponse.setStatusCode(HttpStatus.OK.value());

//               entityResponse =leaveRepository.save();
//               leave.getLeaveReason();
//               leave.getLeaveName();




           }


       } catch (Exception e) {
           //error handling

       }
       return entityResponse;
   }}









//    public EntityResponse<Leave> addLeave( Leave leave){
//        EntityResponse entityResponse= new EntityResponse<>();
//
//        try {






//        }catch (Exception e){
//            //error handling
//        }
//        return entityResponse;
//    }


