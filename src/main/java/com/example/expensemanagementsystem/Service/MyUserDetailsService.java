package com.example.expensemanagementsystem.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.expensemanagementsystem.Model.UserPrincipal;
import com.example.expensemanagementsystem.Model.Users;
import com.example.expensemanagementsystem.Repository.UserDetailsRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserDetailsRepo userrepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user=userrepo.getByUsername(username);
        if(user.isPresent()){
            return new UserPrincipal(user.get());
        }
        else{
            throw new UsernameNotFoundException("User not found");
        }
    }

}
