package com.example.Success18.EmployeeLeave;

import com.example.Success18.Utilities.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
@Slf4j
public class LeaveService {



    private final LeaveRepository leaveRepository;
   @Autowired
   public LeaveService(LeaveRepository leaveRepository){

       this. leaveRepository = leaveRepository;
   }

    public EntityResponse addLeave(Leave leave) {
       EntityResponse entityResponse = new EntityResponse<>();

   try{
       Leave savedLeave=leaveRepository.save(leave);
       entityResponse.setEntity(savedLeave);
       entityResponse.setMessage("Request for leave has been added successfully");
       entityResponse.setStatusCode(HttpStatus.OK.value());


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


