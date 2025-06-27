package com.example.expensemanagementsystem.Model;

import lombok.Data;

@Data
public class Notification {
    private String employeeEmailId;
    private String managerEmailId;
    private String adminEmailId;
}
