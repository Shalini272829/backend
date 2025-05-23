package com.example.expensemanagementsystem.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Expense {

    @Id
    private int expenseId;
    private String type;
    private String description;
    private String amount;
    private LocalDate date;
    
}
