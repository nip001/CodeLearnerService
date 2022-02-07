package com.example.demo.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="daftar")
public class DaftarModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDaftar;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idDosen", referencedColumnName = "idDosen")
    private DosenModel idDosen;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idMahasiswa", referencedColumnName = "idMahasiswa")
    private MahasiswaModel idMahasiswa;

    @Column(nullable = false)
    private Date tanggalDaftar;
}
