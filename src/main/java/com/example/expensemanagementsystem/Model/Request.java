package com.example.expensemanagementsystem.Model;

import lombok.Data;

@Data
public class Request {

    private int statusCode;
    private int id;
    private String token;
    private String role;
    private String username;
    private String password;
    private Employee employee;
}
