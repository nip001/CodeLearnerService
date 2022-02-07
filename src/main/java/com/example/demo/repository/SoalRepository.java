package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.HasilLatihanModel;
import com.example.demo.model.SoalModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SoalRepository extends JpaRepository<SoalModel,Integer>{
    @Query(value="SELECT * FROM soal where id_dosen = ?1",nativeQuery = true)
    public List<SoalModel> findSoalByIdDosen(int idDosen);

    @Query(value="SELECT soal.id_soal,soal.jawaban,soal.judul_soal,soal.tanggal_soal,soal.id_dosen,soal.deskripsi_soal,soal.kelas FROM `soal` "
    +" JOIN daftar ON daftar.id_dosen = soal.id_dosen"
    +" JOIN dosen ON dosen.id_dosen = soal.id_dosen"
    +" JOIN mahasiswa ON mahasiswa.id_mahasiswa =daftar.id_mahasiswa"
    +" LEFT JOIN hasil_latihan ON hasil_latihan.id_soal = soal.id_soal"
    +" WHERE mahasiswa.kelas = soal.kelas AND mahasiswa.id_mahasiswa = ?1 AND dosen.nama_dosen LIKE %?2% AND hasil_latihan.id_hasil is NULL",nativeQuery = true)
    public List<SoalModel> findSoalByNamaDosen(int idMahasiswa,String namaDosen);

    @Query(value="SELECT * FROM soal where id_dosen = ?1 AND kelas LIKE ?2%",nativeQuery = true)
    public List<SoalModel> findSoalByIdDosenANDKelas(int idDosen,String kelas);

    @Query(value="SELECT soal.id_soal,soal.jawaban,soal.judul_soal,soal.tanggal_soal,soal.id_dosen,soal.deskripsi_soal,soal.kelas FROM `soal` "
    +" JOIN daftar ON daftar.id_dosen = soal.id_dosen"
    +" JOIN dosen ON dosen.id_dosen = soal.id_dosen"
    +" JOIN mahasiswa ON mahasiswa.id_mahasiswa =daftar.id_mahasiswa"
    +" LEFT JOIN hasil_latihan ON hasil_latihan.id_soal = soal.id_soal"
    +" WHERE mahasiswa.kelas = soal.kelas AND mahasiswa.id_mahasiswa = ?1 AND hasil_latihan.id_hasil is NULL",nativeQuery = true)
    public List<SoalModel> findSoalByIdMahasiswa(int idMahasiswa);

}
