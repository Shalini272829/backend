package com.example.expensemanagementsystem.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.expensemanagementsystem.Model.ExpenseReportReq;

@Repository
public interface ExpenseRequestRepository extends JpaRepository<ExpenseReportReq,Integer>{

    // @Query(nativeQuery=true, value="SELECT * FROM ExpenseReportReq where status IN ")
    // Optional<ExpenseReportReq> findPendingRequests();
}
