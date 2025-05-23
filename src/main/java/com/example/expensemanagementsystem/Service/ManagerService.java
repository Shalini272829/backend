package com.example.expensemanagementsystem.Service;

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

    public List<ExpenseReportReq> getExpenseRequests(){
        return expreqrepo.findAll();
    }

    // public Optional<ExpenseReportReq> getPendingExpenseRequests(){
    //     return expreqrepo.findPendingRequests();
    // }

    public String editStatusApprove(Integer expRequestId){
        Optional<ExpenseReportReq> optionalrequest=expreqrepo.findById(expRequestId);
        if(optionalrequest.isPresent()){
            optionalrequest.get().setManager_status("Approved");
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
            expreqrepo.save(optionalrequest.get());
            return "The request is rejected by manager";
        }
        else{
            throw new EntityNotFoundException("Such request is not present");
        }
    }
}
