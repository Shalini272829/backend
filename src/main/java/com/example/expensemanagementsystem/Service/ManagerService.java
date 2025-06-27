package com.example.expensemanagementsystem.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.expensemanagementsystem.Model.ExpenseReportReq;
import com.example.expensemanagementsystem.Repository.ExpenseRequestRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ManagerService {

    @Autowired
    ExpenseRequestRepository expreqrepo;

    public List<ExpenseReportReq> getExpenseRequests(Long empId){
        return expreqrepo.findAllExpenses(empId);
    }

    public List<ExpenseReportReq> getPendingExpenseRequests(Long empId){
        String status="pending";
        return expreqrepo.findPendingRequests(empId,status);
    }

    public List<ExpenseReportReq> getApprovedExpenseRequests(Long empId){
        String status="Approved";
        return expreqrepo.findApprovedRequests(empId,status);
    }

    public List<ExpenseReportReq> getRejectedExpenseRequests(Long empId){
        String status="Rejected";
        return expreqrepo.findRejectedRequests(empId,status);
    }

    public String editStatusApprove(int expRequestId){
        Optional<ExpenseReportReq> optionalrequest=expreqrepo.findById(expRequestId);
        if(optionalrequest.isPresent()){
            optionalrequest.get().setManager_status("Approved");
            optionalrequest.get().setApprovedDate(LocalDate.now());
            expreqrepo.save(optionalrequest.get());
            return "The request is approved by manager and is pending with admin";
        }
        else{
            throw new EntityNotFoundException("Such request is not present");
        }
    }

    public String editStatusReject(Integer expRequestId){
        Optional<ExpenseReportReq> optionalrequest=expreqrepo.findById(expRequestId);
        if(optionalrequest.isPresent()){
            optionalrequest.get().setManager_status("Rejected");
            optionalrequest.get().setRejectedDate(LocalDate.now());
            expreqrepo.save(optionalrequest.get());
            return "The request is rejected by manager";
        }
        else{
            throw new EntityNotFoundException("Such request is not present");
        }
    }
}
