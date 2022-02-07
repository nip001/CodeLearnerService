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
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="hasil_latihan")
public class HasilLatihanModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idHasil;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idSoal", referencedColumnName = "idSoal")
    private SoalModel idSoal;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idMahasiswa", referencedColumnName = "idMahasiswa")
    private MahasiswaModel idMahasiswa;

    @Column(nullable = false)
    private String hasilCode;

    @Column(nullable = false)
    private String jawabanMahasiswa;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String keteranganStatus;

    @Column(nullable = false)
    private Date tanggalSubmit;
    

}
