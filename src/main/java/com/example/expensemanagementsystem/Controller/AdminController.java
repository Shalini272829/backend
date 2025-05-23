package com.example.expensemanagementsystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.expensemanagementsystem.Model.Employee;
import com.example.expensemanagementsystem.Service.AdminService;
import jakarta.persistence.EntityNotFoundException;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/admin/get/employeeDetails")
    public ResponseEntity<?> getEmployeeDetails(){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getEmployeeDetails());
    }

    @PostMapping("/admin/add/employee")
    public ResponseEntity<?> postEmployeeDetails(@RequestBody Employee employee){
        Employee createdEmployee=adminService.postEmployeeDetails(employee);
        if(createdEmployee!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/admin/edit/employee/{empId}")
    public ResponseEntity<?> editEmployeeDetails(@PathVariable Long empId, @RequestBody Employee emp){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(adminService.editEmployeeDetails(empId,emp));
        }
        catch(EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/admin/delete/employee/{empId}")
    public ResponseEntity<?> deleteEmployeeDetails(@PathVariable Long empId){
        try{
            adminService.deleteEmployeeDetails(empId);
            return ResponseEntity.status(HttpStatus.OK).body("Employee deleted");
        }
        catch(EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
  

    @GetMapping("/admin/get/expenserequests")
    public ResponseEntity<?> getExpenseRequests(){
       return ResponseEntity.status(HttpStatus.OK).body(adminService.getExpenseRequests());
    }

    // @GetMapping("/admin/get/pending/expenserequests")
    // public ResponseEntity<?> getPendingExpenseRequests(){
    //     return ResponseEntity.status(HttpStatus.OK).body(adminService.getPendingExpenseRequests());
    // }

    @PutMapping("/admin/editStatus/approve/expenserequests/{expRequestId}")
    public ResponseEntity<?> editStatusAccept(@PathVariable Integer expRequestId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(adminService.editStatusApprove(expRequestId));
        }
        catch(EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/admin/editStatus/reject/expenserequests/{expRequestId}")
    public ResponseEntity<?> editStatusReject(@PathVariable Integer expRequestId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(adminService.editStatusReject(expRequestId));
        }
        catch(EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

}
