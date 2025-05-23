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

import com.example.expensemanagementsystem.Model.ExpenseReportReq;
import com.example.expensemanagementsystem.Service.EmployeeService;

import jakarta.persistence.EntityNotFoundException;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("employee/get/details/{empid}")          //employee get own information
    public ResponseEntity<?> getDetails(@PathVariable Long empid){
        try{
            return ResponseEntity.ok(employeeService.getDetails(empid));
        }
        catch(EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

    }

    @PostMapping("employee/add/expenserequest")          // post expense data
    public ResponseEntity<?> postExpenseRequest(@RequestBody ExpenseReportReq ereq){
        ExpenseReportReq createdExpenseReportReq=employeeService.postExpenseRequest(ereq);
        if(createdExpenseReportReq!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(createdExpenseReportReq);
        }
        else{
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/employee/edit/expenserequest/{expRequestId}")     //edit expense data by expenseID
    public ResponseEntity<?> editExpenseRequest(@PathVariable Integer expRequestId, @RequestBody ExpenseReportReq ereq){
        try{
            return ResponseEntity.ok(employeeService.editExpenseRequest(expRequestId, ereq));
        }
        catch(EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/employee/delete/expenserequest/{expRequestId}")  //delete expense data by expenseID
    public ResponseEntity<?> deleteExpenseRequest(@PathVariable Integer expRequestId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.deleteExpenseRequest(expRequestId));
        }
        catch(EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/employee/get/expenserequest/{expRequestId}")    // get expense data by expenseID
    public ResponseEntity<?> getExpenseRequest(@PathVariable Integer expRequestId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.getExpenseRequest(expRequestId));
        }
        catch(EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    // @GetMapping("/employee/getallExpense/{empId}")
    // public ResponseEntity<?> getAllExpenses(@PathVariable Long empId){
    //     try{
    //         return ResponseEntity.status(HttpStatus.OK).body(employeeService.getAllExpense(empId));
    //     }
    // }


}
