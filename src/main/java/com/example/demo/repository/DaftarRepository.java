package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.DaftarModel;
import com.example.demo.model.DosenModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DaftarRepository extends JpaRepository<DaftarModel,Integer>{
    @Query(value = "SELECT * FROM `daftar` WHERE id_dosen = ?1 ",nativeQuery = true)
    List<DaftarModel> findDaftarByIdDosen(int idDosen);

    @Query(value = "SELECT * FROM `daftar` WHERE id_mahasiswa = ?1 ",nativeQuery = true)
    List<DaftarModel> findDaftarByIdMahasiswa(int idMahasiswa);
}
