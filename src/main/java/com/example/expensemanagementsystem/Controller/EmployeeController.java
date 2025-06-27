package com.example.expensemanagementsystem.Controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.expensemanagementsystem.Model.ExpenseReportReq;
import com.example.expensemanagementsystem.Model.FileData;
import com.example.expensemanagementsystem.Service.EmployeeService;

import jakarta.annotation.Resource;
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
    public ResponseEntity<?> postExpenseRequest(@RequestBody ExpenseReportReq ereq) {
       
        ExpenseReportReq createdExpenseReportReq=employeeService.postExpenseRequest(ereq);
        if(createdExpenseReportReq!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(createdExpenseReportReq.getExpRequestId());
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

    @GetMapping("/employee/getallExpense/{empId}")
    public ResponseEntity<?> getAllExpenses(@PathVariable Long empId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.getAllExpense(empId));
        }
        catch(EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

     @GetMapping("/employee/getExpenseExcel/{empId}")
    public ResponseEntity<ByteArrayResource> getAllExpensesInExcel(@PathVariable Long empId) throws IOException{
            ByteArrayOutputStream outputStream=employeeService.getAllExpenseInExcel(empId);
            ByteArrayResource resource=new ByteArrayResource(outputStream.toByteArray());
            HttpHeaders headers=new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=expenseList.xlsx");
            return ResponseEntity.status(HttpStatus.OK).headers(headers).contentLength(resource.contentLength())
            .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
            .body(resource);
    }

    @GetMapping("/employee/approvalanalytics/{empId}")
    public String approvalAnalytics(@PathVariable Long empId){
        return employeeService.approvalAnalytics(empId);
        // return ResponseEntity.status(HttpStatus.OK).body(employeeService.approvalAnalytics(empId));
    }

    @GetMapping("/employee/categoryanalytics/{empId}")
    public String categoryAnalytics(@PathVariable Long empId){
        return employeeService.categoryAnalytics(empId);
        // return ResponseEntity.status(HttpStatus.OK).body(employeeService.approvalAnalytics(empId));
    }

    @GetMapping("/employee/monthlyanalytics/{empId}/{year}")
    public String monthlyAnalytics(@PathVariable Long empId, @PathVariable Long year){
        return employeeService.monthlyAnalytics(empId,year);
        // return ResponseEntity.status(HttpStatus.OK).body(employeeService.approvalAnalytics(empId));
    }

    // @GetMapping("/employee/monthlyanalytics/{empId}/{year}")
    // public ResponseEntity<?> excelMonthlyAnalytics(@PathVariable Long empId, @PathVariable Long year){
    //     // return employeeService.monthlyAnalytics(empId,year);
    //     return ResponseEntity.status(HttpStatus.OK).body(employeeService.excelMonthlyAnalytics(empId,year));
    // }

    @GetMapping("/employee/topexpenses/{empId}")
    public ResponseEntity<?> getTopExpenses(@PathVariable Long empId){
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getTopExpenses(empId));
    }

    // @PostMapping("/public/fileupload/{expenseId}")
    // public ResponseEntity<?> fileUpload(@PathVariable Integer expenseId,@RequestParam MultipartFile file) throws IOException{
    //      return ResponseEntity.ok(employeeService.uploadFile(expenseId,file));

    // }

    @GetMapping("public/filedownload/{expRequestId}")
    public ResponseEntity<?> filedownload(@PathVariable Integer expRequestId)throws IOException{
        byte[] file=employeeService.filedownload(expRequestId);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(file);
    }
}
