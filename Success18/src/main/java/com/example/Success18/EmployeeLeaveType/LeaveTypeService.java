package com.example.Success18.EmployeeLeaveType;

import com.example.Success18.EmployeeLeaveType.LeaveType;
import com.example.Success18.EmployeeLeaveType.LeaveTypeRepository;
import com.example.Success18.Utilities.EntityResponse;
import com.example.Success18.Utilities.LeaveTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LeaveTypeService {

    private final LeaveTypeRepository leaveTypeRepository;
    @Autowired
    public LeaveTypeService(LeaveTypeRepository leaveTypeRepository){

        this.leaveTypeRepository=leaveTypeRepository;
    }
    public EntityResponse add(LeaveType leaveType) {
        try {
            EntityResponse res = new EntityResponse();
            if (leaveType.getLeaveType()== LeaveTypeEnum.OTHER){
                String leaveDescription = leaveType.getLeaveDescription();
                leaveDescription = leaveDescription.replace("_", " ");
                leaveType.setLeaveDescription(leaveDescription);
                LeaveType leaveType1 = leaveTypeRepository.save(leaveType);
                res.setMessage(HttpStatus.CREATED.getReasonPhrase());
                res.setStatusCode(HttpStatus.CREATED.value());
                res.setEntity(leaveType1);
            } else {

                Optional<LeaveType> checkLeaveType = leaveTypeRepository.findByLeaveTypeAndDeletedFlag(leaveType.getLeaveType(), 'N');
                if (checkLeaveType.isPresent()){
                    res.setMessage(leaveType.getLeaveType() + " Already Configured!");
                    res.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                    res.setEntity("");
                } else {

                    if (leaveType.getLeaveType()== LeaveTypeEnum.UNPAID_LEAVE){
                        leaveType.setHasAllowance('N');
                        leaveType.setAllowanceAmount(0.0);
                        leaveType.setFullPaymentFirstMonth('N');

                        LeaveType leaveType1 = leaveTypeRepository.save(leaveType);
                        res.setMessage(HttpStatus.CREATED.getReasonPhrase());
                        res.setStatusCode(HttpStatus.CREATED.value());
                        res.setEntity(leaveType1);
                    }else{
                        if (leaveType.getMonthlyRate() != null){
                            Double deductionPercentage = leaveType.getMonthlyRate();
                            if (deductionPercentage > 100) {
                                res.setMessage("Monthly Rate Percentage Cannot Be Greater Than 100%!");
                                res.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                                res.setEntity("");
                                return res;
                            } else if (deductionPercentage < 0) {
                                res.setMessage("Monthly Rate Percentage Cannot Be Less Than 0!");
                                res.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                                res.setEntity("");
                                return res;
                            }
                        }
                        if (leaveType.getLeaveType()==LeaveTypeEnum.ANNUAL_LEAVE){
                            if (leaveType.getDurationAllowed() == null){
                                res.setMessage("Allowed Period Cannot Be Null For Annual Leave!");
                                res.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                                res.setEntity("");
                            } else {
                                LeaveType leaveType1 = leaveTypeRepository.save(leaveType);
                                res.setMessage(HttpStatus.CREATED.getReasonPhrase());
                                res.setStatusCode(HttpStatus.CREATED.value());
                                res.setEntity(leaveType1);
                            }
                        } else {
                            LeaveType leaveType1 = leaveTypeRepository.save(leaveType);
                            res.setMessage(HttpStatus.CREATED.getReasonPhrase());
                            res.setStatusCode(HttpStatus.CREATED.value());
                            res.setEntity(leaveType1);
                        }
                    }
                }

            }
            return res;
        } catch (Exception e) {
            //log.info("Error {} " + e);
            return null;
        }
    }

    public EntityResponse read() {
        try {
            EntityResponse res = new EntityResponse();
            List<LeaveType> leaveTypes = leaveTypeRepository.findByDeletedFlag('N');
            if (leaveTypes.size()<0){
                res.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                res.setStatusCode(HttpStatus.NOT_FOUND.value());
                res.setEntity("");
            } else {
                res.setMessage(HttpStatus.FOUND.getReasonPhrase());
                res.setStatusCode(HttpStatus.FOUND.value());
                res.setEntity(leaveTypes);
            }
            return res;
        } catch (Exception e) {
            //log.info("Error {} " + e);
            return null;
        }
    }
    public EntityResponse readEnabled() {
        try {
            EntityResponse res = new EntityResponse();
            List<LeaveType> leaveTypes = leaveTypeRepository.findByVerifiedFlagAndDeletedFlagAndIsEnabled('Y', 'N', true);
            if (leaveTypes.size()>0){
                res.setMessage(HttpStatus.FOUND.getReasonPhrase());
                res.setStatusCode(HttpStatus.FOUND.value());
                res.setEntity(leaveTypes);
            } else {
                res.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                res.setStatusCode(HttpStatus.NOT_FOUND.value());
                res.setEntity("");
            }
            return res;
        } catch (Exception e) {
            //log.info("Error {} " + e);
            return null;
        }
    }
    public EntityResponse readById(Long id) {
        try {
            EntityResponse res = new EntityResponse();
            Optional<LeaveType> leaveTypeCheck = leaveTypeRepository.findById(id);
            if (leaveTypeCheck.isPresent()){
                res.setMessage(HttpStatus.FOUND.getReasonPhrase());
                res.setStatusCode(HttpStatus.FOUND.value());
                res.setEntity(leaveTypeCheck.get());
            } else {
                res.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                res.setStatusCode(HttpStatus.NOT_FOUND.value());
                res.setEntity("");
            }
            return res;
        } catch (Exception e) {
            //log.info("Error {} " + e);
            return null;
        }
    }
    public EntityResponse update(LeaveType leaveType) {
        try {
            EntityResponse response = new EntityResponse();
            Optional<LeaveType> searchId = leaveTypeRepository.findById(leaveType.getId());
            Double deductionPercentage;
            if (searchId.isPresent()) {
                if (leaveType.getMonthlyRate() != null){
                    deductionPercentage = leaveType.getMonthlyRate();
                    if (deductionPercentage > 100) {
                        response.setMessage("Monthly Rate Percentage Cannot Be Greater Than 100%!");
                        response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                        response.setEntity("");
                        return response;
                    } else if (deductionPercentage < 0) {
                        response.setMessage("Monthly Rate Percentage Cannot Be Less Than 0!");
                        response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                        response.setEntity("");
                        return response;
                    }
                }
                LeaveType leaveTypeToUpdate = searchId.get();
                leaveTypeToUpdate.setModifiedBy("HRM");
                leaveTypeToUpdate.setModifiedTime(new Date());
                leaveTypeToUpdate.setModifiedFlag('Y');


                if (leaveType.getLeaveType()==LeaveTypeEnum.OTHER){
                    leaveTypeToUpdate.setLeaveType(leaveType.getLeaveType());
                    leaveTypeToUpdate.setDurationAllowed(leaveType.getDurationAllowed());
                    leaveTypeToUpdate.setHasPeriod(leaveType.getHasPeriod());
                    leaveTypeToUpdate.setIncurDeduction(leaveType.getIncurDeduction());
                    leaveTypeToUpdate.setFullPaymentFirstMonth(leaveType.getFullPaymentFirstMonth());
//                    leaveTypeToUpdate.setMonthlyRateIsForPrecedingMonthsOnly(leaveType.getMonthlyRateIsForPrecedingMonthsOnly());
                    leaveTypeToUpdate.setMonthlyRate(leaveType.getMonthlyRate());
                    leaveTypeToUpdate.setHasAllowance(leaveType.getHasAllowance());
                    leaveTypeToUpdate.setThresholdDays(leaveType.getThresholdDays());
                    leaveTypeToUpdate.setAllowanceAmount(leaveType.getAllowanceAmount());

                    String leaveDescription = leaveType.getLeaveDescription();
                    leaveDescription = leaveDescription.replace("_", " ");
                    leaveTypeToUpdate.setLeaveDescription(leaveDescription);
                    LeaveType updatedLeaveType = leaveTypeRepository.save(leaveTypeToUpdate);
                    response.setMessage("Leave Type With Description " + leaveType.getLeaveDescription() + " Modified Successfully On " + updatedLeaveType.getModifiedTime());
                    response.setStatusCode(HttpStatus.OK.value());
                    response.setEntity(updatedLeaveType);
                } else {
                    Optional<LeaveType> checkLeaveType = leaveTypeRepository.findByLeaveTypeAndDeletedFlag(leaveType.getLeaveType(), 'N');
                    if (checkLeaveType.isPresent() && !leaveTypeToUpdate.getLeaveType().equals(leaveType.getLeaveType())){
                        response.setMessage(leaveType.getLeaveType() + " Already Configured!");
                        response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                        response.setEntity("");
                    } else {
                        if (leaveType.getLeaveType()==LeaveTypeEnum.ANNUAL_LEAVE){
                            if (leaveType.getDurationAllowed() == null){
                                response.setMessage("Allowed Period can not be null for Annual Leave");
                                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                                response.setEntity("");
                            } else {
                                leaveTypeToUpdate.setLeaveType(leaveType.getLeaveType());
                                leaveTypeToUpdate.setDurationAllowed(leaveType.getDurationAllowed());
                                leaveTypeToUpdate.setHasPeriod(leaveType.getHasPeriod());
                                leaveTypeToUpdate.setIncurDeduction(leaveType.getIncurDeduction());
                                leaveTypeToUpdate.setFullPaymentFirstMonth(leaveType.getFullPaymentFirstMonth());
//                                leaveTypeToUpdate.setMonthlyRateIsForPrecedingMonthsOnly(leaveType.getMonthlyRateIsForPrecedingMonthsOnly());
                                leaveTypeToUpdate.setMonthlyRate(leaveType.getMonthlyRate());
                                leaveTypeToUpdate.setHasAllowance(leaveType.getHasAllowance());
                                leaveTypeToUpdate.setThresholdDays(leaveType.getThresholdDays());
                                leaveTypeToUpdate.setAllowanceAmount(leaveType.getAllowanceAmount());

                                LeaveType updatedLeaveType = leaveTypeRepository.save(leaveTypeToUpdate);
                                response.setMessage(updatedLeaveType.getLeaveType() + " Modified Successfully On " + updatedLeaveType.getModifiedTime());
                                response.setStatusCode(HttpStatus.OK.value());
                                response.setEntity(updatedLeaveType);
                            }
                        } else {
                            leaveTypeToUpdate.setLeaveType(leaveType.getLeaveType());
                            leaveTypeToUpdate.setDurationAllowed(leaveType.getDurationAllowed());
                            leaveTypeToUpdate.setHasPeriod(leaveType.getHasPeriod());
                            leaveTypeToUpdate.setIncurDeduction(leaveType.getIncurDeduction());
                            leaveTypeToUpdate.setFullPaymentFirstMonth(leaveType.getFullPaymentFirstMonth());
//                            leaveTypeToUpdate.setMonthlyRateIsForPrecedingMonthsOnly(leaveType.getMonthlyRateIsForPrecedingMonthsOnly());
                            leaveTypeToUpdate.setMonthlyRate(leaveType.getMonthlyRate());
                            leaveTypeToUpdate.setHasAllowance(leaveType.getHasAllowance());
                            leaveTypeToUpdate.setThresholdDays(leaveType.getThresholdDays());
                            leaveTypeToUpdate.setAllowanceAmount(leaveType.getAllowanceAmount());

                            LeaveType updatedLeaveType = leaveTypeRepository.save(leaveTypeToUpdate);
                            response.setMessage(updatedLeaveType.getLeaveType() + " Modified Successfully On " + updatedLeaveType.getModifiedTime());
                            response.setStatusCode(HttpStatus.OK.value());
                            response.setEntity(updatedLeaveType);
                        }

                    }

                }
            } else {
                response.setMessage("Leave Type Configuration NOT FOUND!");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setEntity("");
            }
            return response;
        } catch (Exception e) {
            //log.info("Error {} " + e);
            return null;
        }
    }

    public EntityResponse verify(Long id) {
        try {
            EntityResponse response = new EntityResponse();
            Optional<LeaveType> searchId = leaveTypeRepository.findById(id);
            if (searchId.isPresent()) {
                LeaveType leaveType = searchId.get();
                if (leaveType.getVerifiedFlag().equals('Y')) {
                    response.setMessage("Leave Type Configuration For " + leaveType.getLeaveType() + " Already Approved!");
                    response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                    response.setEntity("");
                } else {
                    leaveType.setVerifiedFlag('Y');
                    leaveType.setVerifiedBy("HRM");
                    leaveType.setVerifiedTime(new Date());
                    leaveType.setStatus("Approved");
                    leaveType.setIsEnabled(true);
                    LeaveType leaveType1 = leaveTypeRepository.save(leaveType);
                    response.setMessage(HttpStatus.OK.getReasonPhrase());
                    response.setStatusCode(HttpStatus.OK.value());
                    response.setEntity(leaveType1);
                }

            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setEntity("");
            }
            return response;
        } catch (Exception e) {
//            log.info("Error {} " + e);
//            return null;
        }
        return null;
    }

    public EntityResponse switchEnable(Long id, Boolean isEnabled) {
        try {
            EntityResponse response = new EntityResponse();
            Optional<LeaveType> searchId = leaveTypeRepository.findById(id)
                    ;
            if (searchId.isPresent()){
                LeaveType leaveType = searchId.get();
                leaveType.setIsEnabled(isEnabled);
                LeaveType leaveType1 = leaveTypeRepository.save(leaveType);
                response.setMessage(HttpStatus.OK.getReasonPhrase());
                response.setStatusCode(HttpStatus.OK.value());
                response.setEntity(leaveType1);
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setEntity("");
            }
            return response;
        } catch (Exception e) {
            //log.info("Error {} " + e);
            return null;
        }
    }
    public EntityResponse delete(@PathVariable("id") Long id) {
        try {
            EntityResponse response = new EntityResponse();
            Optional<LeaveType> searchId = leaveTypeRepository.findById(id)
                    ;
            if (searchId.isPresent()) {
                LeaveType leaveType = searchId.get();
                if (leaveType.getStatus().equals("Dormant")) {
                    response.setMessage("Leave Type Configuration For " + leaveType.getLeaveType() + " Already Deactivated On " + leaveType.getDeletedTime() + "!");
                    response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                    response.setEntity("");
                } else {
                    leaveType.setDeletedBy("HRM");
                    leaveType.setDeletedFlag('Y');
                    leaveType.setDeletedTime(new Date());
                    leaveType.setStatus("Dormant");
                    LeaveType leaveType1 = leaveTypeRepository.save(leaveType);
                    response.setMessage("Leave Type Configuration For " + leaveType.getLeaveType() + " Deleted Successfully On " + leaveType1.getDeletedTime());
                    response.setStatusCode(HttpStatus.OK.value());
                    response.setEntity(leaveType1);
                }
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setEntity("");
            }
            return response;
        } catch (Exception e) {
            //log.info("Error {} " + e);
            return null;
        }
    }
}


//    public List<LeaveType> getAllLeaveTypes() {
//        return leaveTypeRepository.findAll();
//    }
//
//    public LeaveType getLeaveTypeById(Long id) {
//        Optional<LeaveType> leaveTypeOptional = leaveTypeRepository.findById(id);
//        return leaveTypeOptional.orElse(null);
//    }
//
//    public LeaveType createLeaveType(LeaveType leaveType) {
//            return leaveTypeRepository.save(leaveType);
//
//    }
//
//    public LeaveType updateLeaveType(Long id, LeaveType updatedLeaveType) {
//        if (leaveTypeRepository.existsById(id)) {
//            updatedLeaveType.setId(id);
//            return leaveTypeRepository.save(updatedLeaveType);
//        }
//        return null;
//    }
//
//    public void deleteLeaveType(Long id) {
//        leaveTypeRepository.deleteById(id);
//    }
//
//}
