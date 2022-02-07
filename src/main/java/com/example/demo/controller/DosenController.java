package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.DosenModel;
import com.example.demo.repository.DosenRepository;
import com.example.demo.utility.FileUtility;
import com.example.demo.utility.UtilityString;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class DosenController {
    
    @Autowired
    DosenRepository dosenRepo;

    @Autowired
    PasswordEncoder passwordEncoder;
    

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
    
    @PostMapping("/data/dosen/register")
    public ResponseEntity<String> tambahDosen(@RequestParam(value="file") MultipartFile images, @ModelAttribute(value="data") String dataJSON) throws IOException{
        String fileName = StringUtils.cleanPath(images.getOriginalFilename());
		
		String uploadDir = "user-photo/";
		FileUtility.saveFile(uploadDir, fileName, images);
		DosenModel dosen= new Gson().fromJson(dataJSON, DosenModel.class);
		
		dosen.setFotoDosen(fileName);
        dosen.setPassword(passwordEncoder.encode(dosen.getPassword()));
		dosenRepo.save(dosen);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{\"message\":\"berhasil ditambahkan\"}");
    }

    @GetMapping("/dosen/")
    public ResponseEntity<List<DosenModel>> getAllDosen(){
        return ResponseEntity.ok().body(dosenRepo.findAll());
    }

    @GetMapping("/dosen/mahasiswa/{id}")
    public ResponseEntity<List<DosenModel>> getDosenById(HttpServletRequest request,@PathVariable("id") String dataCari){
		final String requestTokenHeader = request.getHeader("Authorization");
        int idMahasiswa =Integer.parseInt(jwtTokenUtil.getUserIdFromToken(requestTokenHeader.substring(7)));
        List<DosenModel> dosen = new ArrayList<DosenModel>();
        if(UtilityString.isNumeric(dataCari)){
            dosen = dosenRepo.findByIdDosen(Integer.parseInt(dataCari),idMahasiswa);
        }else{
            dosen = dosenRepo.findByNamaDosen(dataCari,idMahasiswa);
        }
        return ResponseEntity.ok().body(dosen);
    }
        
    @DeleteMapping("/dosen/{id}")
    public ResponseEntity<String> deleteDosenById(@PathVariable("id") int id){
        dosenRepo.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{\"message\":\"Delete Berhasil\"}");
    }
}
