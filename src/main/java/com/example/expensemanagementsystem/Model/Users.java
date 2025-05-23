package com.example.expensemanagementsystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Users {

    @Id
    private int Id;
    private String username;
    private String password;
    private String role;
}
