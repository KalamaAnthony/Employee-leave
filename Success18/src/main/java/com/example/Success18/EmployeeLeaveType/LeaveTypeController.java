package com.example.Success18.EmployeeLeaveType;

import com.example.Success18.EmployeeLeaveType.LeaveType;
import com.example.Success18.EmployeeLeaveType.LeaveTypeService;
import com.example.Success18.Utilities.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(path = "api/v1/leaveType")
public class LeaveTypeController {
    private final LeaveTypeService leaveTypeService;

    @Autowired
    public LeaveTypeController (LeaveTypeService leaveTypeService){

        this.leaveTypeService=leaveTypeService;
    }
//    @GetMapping
//    public List<LeaveType> getAllLeaveTypes() {
//        return leaveTypeService.getAllLeaveTypes();





    //
    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody LeaveType leaveType) {
        try {
            EntityResponse response = leaveTypeService.add(leaveType);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> read() {
        try {
            EntityResponse response = leaveTypeService.read();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }
    @GetMapping("/all/enabled")
    public ResponseEntity<?> readEnabled() {
        try {
            EntityResponse response = leaveTypeService.readEnabled();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            EntityResponse response = leaveTypeService.readById(id)
                    ;
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody LeaveType leaveType) {
        try {
            EntityResponse response = leaveTypeService.update(leaveType);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }
    @PutMapping("/verify/{id}")
    public ResponseEntity<?> verify(@PathVariable Long id) {
        try {
            EntityResponse response = leaveTypeService.verify(id)
                    ;
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }

    @PutMapping("/switchEnabled")
    public ResponseEntity<?> switchEnabled(@RequestParam Long id, @RequestParam Boolean isEnabled) {
        try {
            EntityResponse response = leaveTypeService.switchEnable(id, isEnabled);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> tempDeleteLeaveType(@PathVariable("id") Long id) {
        try {
            EntityResponse response = leaveTypeService.delete(id)
                    ;
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }
}




//    @GetMapping("/{id}")
//    public LeaveType getLeaveTypeById(@PathVariable Long id) {
//        return leaveTypeService.getLeaveTypeById(id);
//    }
//
//    @PostMapping
//    public LeaveType createLeaveType(@RequestBody LeaveType leaveType) {
//        return leaveTypeService.createLeaveType(leaveType);
//    }
//
//    @PutMapping("/{id}")
//    public LeaveType updateLeaveType(@PathVariable Long id, @RequestBody LeaveType leaveType) {
//        return leaveTypeService.updateLeaveType(id, leaveType);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteLeaveType(@PathVariable Long id) {
//        leaveTypeService.deleteLeaveType(id);
//    }
//}
