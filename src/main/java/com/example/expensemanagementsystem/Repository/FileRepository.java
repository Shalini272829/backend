package com.example.expensemanagementsystem.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.expensemanagementsystem.Model.FileData;

public interface FileRepository extends JpaRepository<FileData,Integer>{

    Optional<FileData> findByName(String filename);
}
