package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.HasilLatihanModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HasilLatihanRepository extends JpaRepository<HasilLatihanModel, Integer>{
    @Query(value="SELECT * FROM hasil_latihan "
    + "INNER JOIN soal ON soal.id_soal = hasil_latihan.id_soal "
    + "INNER JOIN mahasiswa ON mahasiswa.id_mahasiswa = hasil_latihan.id_mahasiswa "
    + "WHERE soal.id_dosen= ?1",nativeQuery = true)
    public List<HasilLatihanModel> findHasilLatihanByIdDosen(int idDosen);

    @Query(value="SELECT * FROM hasil_latihan "
    + "INNER JOIN soal ON soal.id_soal = hasil_latihan.id_soal "
    + "INNER JOIN mahasiswa ON mahasiswa.id_mahasiswa = hasil_latihan.id_mahasiswa "
    + "WHERE hasil_latihan.id_mahasiswa=?1 AND soal.id_dosen= ?2",nativeQuery = true)
    public List<HasilLatihanModel> findHasilLatihanByIdMahasiswaAndDosen(int idMahasiswa,int idDosen);

    @Query(value="SELECT * FROM hasil_latihan "
    + "INNER JOIN soal ON soal.id_soal = hasil_latihan.id_soal "
    + "INNER JOIN mahasiswa ON mahasiswa.id_mahasiswa = hasil_latihan.id_mahasiswa "
    + "WHERE hasil_latihan.id_mahasiswa=?1 ",nativeQuery = true)
    public List<HasilLatihanModel> findHasilLatihanByIdMahasiswa(int idMahasiswa);

    
    @Query(value="SELECT hasil_latihan.* FROM `hasil_latihan`" 
    +" INNER JOIN soal on soal.id_soal = hasil_latihan.id_soal"
    +" WHERE hasil_latihan.id_mahasiswa = ?1 AND soal.id_dosen =?2",nativeQuery = true)
    public List<HasilLatihanModel> findHasilLatihanByIdMahasiswaAndDosen2(int idMahasiswa,int idDosen);

    
    // @Query(value="SELECT hasil_latihan.id_hasil,hasil_latihan.hasil_code,hasil_latihan.jawaban_mahasiswa,hasil_latihan.keterangan_status,hasil_latihan.status,hasil_latihan.tanggal_submit,hasil_latihan.id_mahasiswa,soal.* FROM `soal` "
    // +" JOIN daftar ON daftar.id_dosen = soal.id_dosen"
    // +" JOIN dosen ON dosen.id_dosen = soal.id_dosen"
    // +" JOIN mahasiswa ON mahasiswa.id_mahasiswa =daftar.id_mahasiswa"
    // +" LEFT JOIN hasil_latihan ON hasil_latihan.id_soal = soal.id_soal"
    // +" WHERE mahasiswa.kelas = soal.kelas AND mahasiswa.id_mahasiswa = ?1 AND soal.id_dosen = ?2",nativeQuery = true)
    // public List<HasilLatihanModel> findSoalByIdMahasiswaAndIdDosen(int idMahasiswa, int idDosen);
}
