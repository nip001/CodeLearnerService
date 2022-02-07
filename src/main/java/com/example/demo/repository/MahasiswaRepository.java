package com.example.demo.repository;

import com.example.demo.model.MahasiswaModel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MahasiswaRepository extends JpaRepository<MahasiswaModel,Integer>{
    
    public MahasiswaModel findByUsername(String username);
}
