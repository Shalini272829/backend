package com.example.expensemanagementsystem.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class ExpenseReportReq {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer ExpRequestId;
    private String type;
    private String description;
    private String Amount;
    private LocalDate exp_date;
    private String fileName;
    private String fileType;
    private String filePath;
    private Long empId;
    private String empName;
    private String status;
    private Long managerEmpId;
    private Long adminEmpId;
    private String manager_status;
    private String admin_status;
    private LocalDate approvedDate;
    private LocalDate rejectedDate;
    private String rejectedReason;
    private LocalDate cutoffDate;
    private LocalDate submissionDate;
}
