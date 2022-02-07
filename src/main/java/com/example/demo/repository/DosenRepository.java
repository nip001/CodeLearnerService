package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.DosenModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DosenRepository extends JpaRepository<DosenModel,Integer>{
    public DosenModel findByUsername(String username);
    @Query(value="select dosen.id_dosen,dosen.foto_dosen,dosen.nama_dosen,dosen.username,dosen.password from dosen LEFT JOIN daftar ON daftar.id_dosen = dosen.id_dosen where dosen.nama_dosen LIKE %?1% AND (daftar.id_mahasiswa != ?2 OR daftar.id_daftar is NULL)",nativeQuery = true)
    public List<DosenModel> findByNamaDosen(String namaDosen, int idMahasiswa);
    @Query(value="select dosen.id_dosen,dosen.foto_dosen,dosen.nama_dosen,dosen.username,dosen.password from dosen LEFT JOIN daftar ON daftar.id_dosen = dosen.id_dosen where dosen.id_dosen LIKE %?1% AND (daftar.id_mahasiswa != ?2 OR daftar.id_daftar is NULL)",nativeQuery = true)
    public List<DosenModel> findByIdDosen(int idDosen,int idMahasiswa);
}
