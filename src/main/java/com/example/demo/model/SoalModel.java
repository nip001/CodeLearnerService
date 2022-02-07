package com.example.demo.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "soal")
public class SoalModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSoal;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idDosen", referencedColumnName = "idDosen")
    private DosenModel idDosen;

    @Column(nullable = false)
    private String judulSoal;

    @Column(nullable = false)
    @Lob
    private String deskripsiSoal;

    @Column(nullable = false)
    private String jawaban;

    @Column(nullable = false)
    private Date tanggalSoal;

    @Column(nullable = false)
    private String kelas;
}
