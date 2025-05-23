package com.example.expensemanagementsystem.Service;

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
public class EmployeeService {

    @Autowired
    EmployeeRepository emprepo;

    @Autowired
    ExpenseRequestRepository expreqrepo;

    public Employee getDetails(Long empId){
        Optional<Employee> optionalEmployee=emprepo.findById(empId);
        if(optionalEmployee.isPresent()){
            return optionalEmployee.get();
        }
        else{
            throw new EntityNotFoundException("Employee record is not found with id "+empId);
        }
    }

    public ExpenseReportReq postExpenseRequest(ExpenseReportReq ereq){
        return expreqrepo.save(ereq);
    }

    public ExpenseReportReq editExpenseRequest(Integer expRequestId, ExpenseReportReq ereq){
        Optional<ExpenseReportReq> optionalExpenseRequest=expreqrepo.findById(expRequestId);
        if(optionalExpenseRequest.isPresent()){
            return expreqrepo.save(ereq);
        }
        else{
            throw new EntityNotFoundException("Such request with "+ expRequestId +" is not present. Please check!!");
        }
    }

    public String deleteExpenseRequest(Integer expRequestId){
        Optional<ExpenseReportReq> optionalExpenseRequest=expreqrepo.findById(expRequestId);
        if(optionalExpenseRequest.isPresent()){
            expreqrepo.deleteById(expRequestId);
            return "Mentioned Expense report deleted";
        }
        else{
            throw new EntityNotFoundException("Such request with \"+ expRequestId +\" is not present. Please check!!");
        }
    }

    public ExpenseReportReq getExpenseRequest(Integer expRequestId) {
        Optional<ExpenseReportReq> optionalExpenseRequest=expreqrepo.findById(expRequestId);
        if(optionalExpenseRequest.isPresent())
        return optionalExpenseRequest.get();
        else{
            throw new EntityNotFoundException("Such request with \"+ expRequestId +\" is not present. Please check!!");
        }
    }

    // public List<ExpenseReportReq> getAllExpense(Long empId){
         
    // }
}
