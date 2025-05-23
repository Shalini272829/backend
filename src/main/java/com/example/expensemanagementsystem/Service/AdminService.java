package com.example.expensemanagementsystem.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.expensemanagementsystem.Model.Employee;
import com.example.expensemanagementsystem.Model.ExpenseReportReq;
import com.example.expensemanagementsystem.Repository.EmployeeRepository;
import com.example.expensemanagementsystem.Repository.ExpenseRequestRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AdminService {
    @Autowired
    ExpenseRequestRepository expreqrepo;

    @Autowired
    EmployeeRepository emprepo;

    public List<Employee> getEmployeeDetails(){
        return emprepo.findAll();
    }

    public Employee postEmployeeDetails(Employee emp){
        return emprepo.save(emp);
    }

    public Employee editEmployeeDetails(Long empId, Employee emp){
        Optional<Employee> optionalemployee=emprepo.findById(empId);
        if(optionalemployee.isPresent()){
            return emprepo.save(emp);
        }
        else{
            throw new EntityNotFoundException("Such employee Id is not present. Please Check");
        }
    }

    public void deleteEmployeeDetails(Long empId){
        Optional<Employee> optionalEmployee=emprepo.findById(empId);
        if(optionalEmployee.isPresent()){
            emprepo.deleteById(empId);
        }
        else{
            throw new EntityNotFoundException("Such employee Id is not present. Please Check");
        }
    }


    public List<ExpenseReportReq> getExpenseRequests(){
        return expreqrepo.findAll();
    }

    // public Optional<ExpenseReportReq> getPendingExpenseRequests(){
    //     return expreqrepo.findPendingRequests();
    // }

    public String editStatusApprove(Integer expRequestId){
        Optional<ExpenseReportReq> optionalrequest=expreqrepo.findById(expRequestId);
        if(optionalrequest.isPresent()){
            optionalrequest.get().setAdmin_status("Approved");
            optionalrequest.get().setStatus("Approved");
            optionalrequest.get().setApprovedDate(LocalDate.now());
            expreqrepo.save(optionalrequest.get());
            return "The request is approved by admin";
        }
        else{
            throw new EntityNotFoundException("Such request is not present");
        }
    }

    public String editStatusReject(Integer expRequestId){
        Optional<ExpenseReportReq> optionalrequest=expreqrepo.findById(expRequestId);
        if(optionalrequest.isPresent()){
            optionalrequest.get().setManager_status("Rejected");
            optionalrequest.get().setStatus("Rejected");
            expreqrepo.save(optionalrequest.get());
            return "The request is rejected by admin";
        }
        else{
            throw new EntityNotFoundException("Such request is not present");
        }
    }
}
