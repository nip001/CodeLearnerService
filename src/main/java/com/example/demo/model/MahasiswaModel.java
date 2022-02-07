package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "mahasiswa")
public class MahasiswaModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMahasiswa;

    @Column(nullable = false)
    private String namaMahasiswa;
    
    @Column(nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String fotoMahasiswa;
    
    @Column(nullable = false)
    private String kelas;
}
