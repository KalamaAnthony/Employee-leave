package com.example.Success18.EmployeeLeave;

import com.example.Success18.Utilities.EntityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/leave")
public class LeaveController {

    private final LeaveService leaveService;
    @Autowired
    public LeaveController (LeaveService leaveService){

        this.leaveService =leaveService;
    }


    @PostMapping("/request")
    public EntityResponse addLeave(@RequestBody Leave leave){

        return leaveService.addLeave(leave);

    }
    @PutMapping("/id")
    public EntityResponse updateLeaveById(@RequestBody Leave leave){
        return leaveService.updateLeaveById(leave);

    }
    @PutMapping("/delete")
    public EntityResponse deleteLeaveById(@PathVariable Long id){
        return leaveService.deleteLeaveById(id);
    }


}
