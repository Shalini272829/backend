package com.example.expensemanagementsystem.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.expensemanagementsystem.Model.Users;

@Repository
public interface UserDetailsRepo extends JpaRepository<Users,Integer>{

    Optional<Users> getByUsername(String username);
}
