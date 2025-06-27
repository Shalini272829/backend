package com.example.expensemanagementsystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.expensemanagementsystem.Model.Users;
import com.example.expensemanagementsystem.Service.UserService;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("admin/add/users")
    public ResponseEntity<?> addUser(@RequestBody Users user){
        Users createdUser=userService.addUser(user);
        if(createdUser!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body("User created");
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("public/auth/login")
    public ResponseEntity<?> Login(@RequestBody Users user){
        return ResponseEntity.status(HttpStatus.OK).body(userService.verify(user));
    }

    @PutMapping("/employee/password/reset")
    public ResponseEntity<?> passwordReset(@RequestBody Users user){
        return ResponseEntity.status(HttpStatus.OK).body(userService.passwordReset(user));
    }
}
