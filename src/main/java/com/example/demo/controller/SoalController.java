package com.example.demo.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.DaftarModel;
import com.example.demo.model.DosenModel;
import com.example.demo.model.HasilLatihanModel;
import com.example.demo.model.ResultSoal;
import com.example.demo.model.SoalModel;
import com.example.demo.repository.DaftarRepository;
import com.example.demo.repository.HasilLatihanRepository;
import com.example.demo.repository.SoalRepository;

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
@RequestMapping("/soal")
public class SoalController {

    @Autowired
    SoalRepository soalRepository;

    @Autowired
    DaftarRepository daftarRepository;
    @Autowired
    HasilLatihanRepository hasilatihanRepository;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
    
    @GetMapping("/")
    public ResponseEntity<List<SoalModel>> getAllSoal(HttpServletRequest request){
		final String requestTokenHeader = request.getHeader("Authorization");
        int idDosen =Integer.parseInt(jwtTokenUtil.getUserIdFromToken(requestTokenHeader.substring(7)));
        return ResponseEntity.ok(soalRepository.findSoalByIdDosen(idDosen));
    }

    @GetMapping("/kelas/{kelas}")
    public ResponseEntity<List<SoalModel>> getAllSoalByKelas(HttpServletRequest request,@PathVariable(value="kelas") String kelas){
		final String requestTokenHeader = request.getHeader("Authorization");
        int idDosen =Integer.parseInt(jwtTokenUtil.getUserIdFromToken(requestTokenHeader.substring(7)));
        return ResponseEntity.ok(soalRepository.findSoalByIdDosenANDKelas(idDosen, kelas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SoalModel> getSoalByIdSoal(@PathVariable("id") int id){
        return ResponseEntity.ok(soalRepository.findById(id).get());
    }

    @GetMapping("/dosen/{idDosen}")
    public ResponseEntity<List<SoalModel>> getSoalByIdDosen(@PathVariable("idDosen") int idDosen){
        return ResponseEntity.ok(soalRepository.findSoalByIdDosen(idDosen));
    }

    @GetMapping("/namadosen/{namadosen}")
    public ResponseEntity<List<SoalModel>> getSoalByNamaDosen(HttpServletRequest request,@PathVariable("namadosen") String namaDosen){
		final String requestTokenHeader = request.getHeader("Authorization");
        int idMahasiswa =Integer.parseInt(jwtTokenUtil.getUserIdFromToken(requestTokenHeader.substring(7)));
        return ResponseEntity.ok(soalRepository.findSoalByNamaDosen(idMahasiswa,namaDosen));
    }

    
    @GetMapping("/mahasiswa/")
    public ResponseEntity<List<SoalModel>> getAllSoalMahasiswaFromDosen(HttpServletRequest request){
		final String requestTokenHeader = request.getHeader("Authorization");
        int idMahasiswa =Integer.parseInt(jwtTokenUtil.getUserIdFromToken(requestTokenHeader.substring(7)));
        // List<DaftarModel> daftarModel = daftarRepository.findDaftarByIdMahasiswa(idMahasiswa);
        // List<SoalModel> soal = new ArrayList<SoalModel>();
        // for (int i = 0; i < daftarModel.size(); i++) {
        //     soal.addAll(soalRepository.findSoalByIdDosen(daftarModel.get(i).getIdDosen().getIdDosen()));
        // }
        // return ResponseEntity.ok(soal);

        List<SoalModel> soal = soalRepository.findSoalByIdMahasiswa(idMahasiswa);
        return ResponseEntity.ok(soal);
    }
    
    
    @GetMapping("/mahasiswa/dosen/{id}")
    public ResponseEntity<ResultSoal> getAllSoalMahasiswaFromDosenAndResult(HttpServletRequest request,@PathVariable(value="id") int idDosen){
		final String requestTokenHeader = request.getHeader("Authorization");
        int idMahasiswa =Integer.parseInt(jwtTokenUtil.getUserIdFromToken(requestTokenHeader.substring(7)));
        // List<DaftarModel> daftarModel = daftarRepository.findDaftarByIdMahasiswa(idMahasiswa);
        // List<SoalModel> soal = new ArrayList<SoalModel>();
        // for (int i = 0; i < daftarModel.size(); i++) {
        //     soal.addAll(soalRepository.findSoalByIdDosen(daftarModel.get(i).getIdDosen().getIdDosen()));
        // }
        // return ResponseEntity.ok(soal);
        double pengurangan =0;
        double nilai =0;
        List<HasilLatihanModel> hasilLatihan = hasilatihanRepository.findHasilLatihanByIdMahasiswaAndDosen2(idMahasiswa,idDosen);
        List<SoalModel> soal = soalRepository.findSoalByIdMahasiswa(idMahasiswa);
        if(!hasilLatihan.isEmpty()){
            pengurangan = 100/hasilLatihan.size();
            nilai = 100;
            for (HasilLatihanModel hasilLatihanModel : hasilLatihan) {
                if(hasilLatihanModel.getStatus().equalsIgnoreCase("ERROR")){
                    nilai -= pengurangan;
                }
            }
        }
        return ResponseEntity.ok(new ResultSoal(hasilLatihan,soal,nilai));
    }
    

    @PostMapping("/")
    public ResponseEntity<String> tambahSoal(@RequestBody SoalModel soal,HttpServletRequest request){
        final String requestTokenHeader = request.getHeader("Authorization");
        int idDosen =Integer.parseInt(jwtTokenUtil.getUserIdFromToken(requestTokenHeader.substring(7)));
        soal.setTanggalSoal(new Date(new java.util.Date().getTime()));
        DosenModel dosen = new DosenModel();
        dosen.setIdDosen(idDosen);
        soal.setIdDosen(dosen);
        soalRepository.save(soal);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{\"message\":\"berhasil ditambahkan\"}");
    }
}
