package com.example.expensemanagementsystem.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ExpenseReportReq {

    @Id
    private Integer ExpRequestId;
    private String type;
    private String description;
    private String Amount;
    private LocalDate exp_date;
    private Long empId;
    private String empName;
    private String status;
    private String manager_status;
    private String admin_status;
    private LocalDate approvedDate;
    private LocalDate cutoffDate;
}
