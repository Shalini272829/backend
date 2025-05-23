package com.example.expensemanagementsystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Employee {

    @Id
    private Long employeeId;
    private String username;
    private String empName;
    private String emailID;
    private String role;

}
