package com.example.Success18.EmployeeLeaveType;

import com.example.Success18.EmployeeLeaveType.LeaveType;
import com.example.Success18.Utilities.LeaveTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LeaveTypeRepository  extends JpaRepository <LeaveType, Long> {
    Optional<LeaveType> findByLeaveTypeAndDeletedFlag(LeaveTypeEnum leaveType, Character deletedFlag);
    Optional<LeaveType> findByLeaveDescriptionAndDeletedFlag(String leaveDescription, Character deletedFlag);
    List<LeaveType> findByDeletedFlag(Character deletedFlag);
    List<LeaveType> findByVerifiedFlagAndDeletedFlagAndIsEnabled(Character  verifiedFlag, Character deletedFlag, Boolean isEnabled);


}



