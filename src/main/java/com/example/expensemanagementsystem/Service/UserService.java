package com.example.expensemanagementsystem.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.expensemanagementsystem.Model.Employee;
import com.example.expensemanagementsystem.Model.Request;
import com.example.expensemanagementsystem.Model.Users;
import com.example.expensemanagementsystem.Repository.EmployeeRepository;
import com.example.expensemanagementsystem.Repository.UserDetailsRepo;

@Service
public class UserService {

    @Autowired
    UserDetailsRepo userrepo;

    @Autowired
    EmployeeRepository emprepo;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtService jwtService;
    
    BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder(12);

    public Users addUser(Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userrepo.save(user);
    }

    public Request verify(Users user){
        Request response=new Request();
        Optional<Users> optionaluser=userrepo.getByUsername(user.getUsername());
        Optional<Employee> optionalemployee=emprepo.getByUsername(user.getUsername());
        if(optionaluser.isPresent()){
            Authentication auth=authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
            if(auth.isAuthenticated()){
                response.setToken(jwtService.generateToken(user.getUsername()));
                response.setUsername(optionaluser.get().getUsername());
                response.setPassword(optionaluser.get().getPassword());
                response.setId(optionaluser.get().getId());
                response.setRole(optionaluser.get().getRole());
                response.setStatusCode(200);
                response.setEmployee(optionalemployee.get());
            }
            else{
                response.setStatusCode(500);
            }
        }
        else{
            response.setError("Such User is not present!!");
        }
        return response;
    }

    public String passwordReset(Users user){
        Optional<Users> optionalUser=userrepo.getByUsername(user.getUsername());
        if(optionalUser.isPresent()){
            optionalUser.get().setPassword(passwordEncoder.encode(user.getPassword()));
            userrepo.save(optionalUser.get());
            return "password changes successfully";
        }
        else{
            return "password changes failed";
        }
    }
}
        

    // }


