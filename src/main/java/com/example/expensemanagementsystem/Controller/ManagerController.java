package com.example.expensemanagementsystem.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.expensemanagementsystem.Service.ManagerService;

import jakarta.persistence.EntityNotFoundException;


@RestController
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @GetMapping("/manager/get/expenserequests")
    public ResponseEntity<?> getExpenseRequests(){
       return ResponseEntity.status(HttpStatus.OK).body(managerService.getExpenseRequests());
    }

    // @GetMapping("/manager/get/pending/expenserequests")
    // public ResponseEntity<?> getPendingExpenseRequests(){
    //     return ResponseEntity.status(HttpStatus.OK).body(managerService.getPendingExpenseRequests());
    // }

    @PutMapping("/manager/editStatus/approve/expenserequests/{expRequestId}")
    public ResponseEntity<?> editStatusAccept(@PathVariable Integer expRequestId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(managerService.editStatusApprove(expRequestId));
        }
        catch(EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/manager/editStatus/reject/expenserequests/{expRequestId}")
    public ResponseEntity<?> editStatusReject(@PathVariable Integer expRequestId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(managerService.editStatusReject(expRequestId));
        }
        catch(EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }


}
