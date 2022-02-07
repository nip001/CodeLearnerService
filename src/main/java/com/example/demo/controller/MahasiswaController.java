package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import com.example.demo.model.MahasiswaModel;
import com.example.demo.repository.MahasiswaRepository;
import com.example.demo.utility.FileUtility;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MahasiswaController {
    @Autowired
    MahasiswaRepository mahasiswaRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @PostMapping("data/mahasiswa/register")
    public ResponseEntity<String> tambahMahasiswa(@RequestParam(value="file") MultipartFile images, @ModelAttribute(value="data") String dataJSON) throws IOException{
        String fileName = StringUtils.cleanPath(images.getOriginalFilename());
		
		String uploadDir = "user-photo/";
		FileUtility.saveFile(uploadDir, fileName, images);
		MahasiswaModel mahasiswa= new Gson().fromJson(dataJSON, MahasiswaModel.class);
		
		mahasiswa.setFotoMahasiswa(fileName);
        mahasiswa.setPassword(passwordEncoder.encode(mahasiswa.getPassword()));
		mahasiswaRepo.save(mahasiswa);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{\"message\":\"berhasil ditambahkan\"}");
    }

    @GetMapping("/mahasiswa/")
    public ResponseEntity<List<MahasiswaModel>> getAllMahasiswa(){
        return ResponseEntity.ok().body(mahasiswaRepo.findAll());
    }

    @GetMapping("/mahasiswa/{id}")
    public ResponseEntity<MahasiswaModel> getMahasiswaById(@PathVariable("id") int id){
        return ResponseEntity.ok().body(mahasiswaRepo.findById(id).get());
    }
        
    @DeleteMapping("/mahasiswa/{id}")
    public ResponseEntity<String> deleteMahasiswaById(@PathVariable("id") int id){
        mahasiswaRepo.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{\"message\":\"Delete Berhasil\"}");
    }
}
