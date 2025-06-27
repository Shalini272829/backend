package com.example.expensemanagementsystem.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.expensemanagementsystem.Model.ExpenseReportReq;
import com.example.expensemanagementsystem.Service.ManagerService;

import jakarta.persistence.EntityNotFoundException;


@RestController
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @GetMapping("/manager/get/expenserequests/{empId}")
    public ResponseEntity<?> getExpenseRequests(@PathVariable Long empId){
       return ResponseEntity.status(HttpStatus.OK).body(managerService.getExpenseRequests(empId));
    }

    @GetMapping("/manager/get/pending/expenserequests/{empId}")
    public ResponseEntity<?> getPendingExpenseRequests(@PathVariable Long empId){
        return ResponseEntity.status(HttpStatus.OK).body(managerService.getPendingExpenseRequests(empId));
    }

    @GetMapping("/manager/get/approved/expenserequests/{empId}")
    public ResponseEntity<?> getApprovedExpenseRequests(@PathVariable Long empId){
        return ResponseEntity.status(HttpStatus.OK).body(managerService.getApprovedExpenseRequests(empId));
    }

    @GetMapping("/manager/get/rejected/expenserequests/{empId}")
    public ResponseEntity<?> getRejectedExpenseRequests(@PathVariable Long empId){
        return ResponseEntity.status(HttpStatus.OK).body(managerService.getRejectedExpenseRequests(empId));
    }

    @PutMapping("/manager/editstatus/approve/expenserequests/{expRequestId}")
    public ResponseEntity<?> editStatusAccept(@PathVariable int expRequestId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(managerService.editStatusApprove(expRequestId));
        }
        catch(EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/manager/editstatus/reject/expenserequests/{expRequestId}")
    public ResponseEntity<?> editStatusReject(@PathVariable Integer expRequestId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(managerService.editStatusReject(expRequestId));
        }
        catch(EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }


}
