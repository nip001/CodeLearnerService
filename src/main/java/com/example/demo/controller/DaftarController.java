package com.example.demo.controller;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.DaftarModel;
import com.example.demo.model.DosenModel;
import com.example.demo.model.MahasiswaModel;
import com.example.demo.repository.DaftarRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dosen/daftar")
public class DaftarController {
    
    @Autowired
    DaftarRepository daftarRepository;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
    
    @GetMapping("/")
    public ResponseEntity<List<DaftarModel>> getAllDaftar(HttpServletRequest request){
		final String requestTokenHeader = request.getHeader("Authorization");
        int idDosen =Integer.parseInt(jwtTokenUtil.getUserIdFromToken(requestTokenHeader.substring(7)));
        return ResponseEntity.ok(daftarRepository.findDaftarByIdDosen(idDosen));
    }

    @GetMapping("/mahasiswa/")
    public ResponseEntity<List<DaftarModel>> getAllDaftarMahasiswa(HttpServletRequest request){
		final String requestTokenHeader = request.getHeader("Authorization");
        int idMahasiswa =Integer.parseInt(jwtTokenUtil.getUserIdFromToken(requestTokenHeader.substring(7)));
        return ResponseEntity.ok(daftarRepository.findDaftarByIdMahasiswa(idMahasiswa));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DaftarModel> getDaftarById(@PathVariable(value="id") int id){
        return ResponseEntity.ok(daftarRepository.findById(id).get());
    }

    @PostMapping("/")
    public ResponseEntity<String> tambahDaftarDosen(@RequestBody DaftarModel daftar,HttpServletRequest request){
        daftar.setTanggalDaftar(new Date(new java.util.Date().getTime()));
        final String requestTokenHeader = request.getHeader("Authorization");
        int idMahasiswa =Integer.parseInt(jwtTokenUtil.getUserIdFromToken(requestTokenHeader.substring(7)));

        MahasiswaModel mahasiswa = new MahasiswaModel();
        mahasiswa.setIdMahasiswa(idMahasiswa);
        daftar.setIdMahasiswa(mahasiswa);
        daftarRepository.save(daftar);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{\"message\":\"berhasil ditambahkan\"}");
    }

}
