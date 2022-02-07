package com.example.demo.controller;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.CompilerModel;
import com.example.demo.model.GetScore;
import com.example.demo.model.HasilLatihanModel;
import com.example.demo.model.MahasiswaModel;
import com.example.demo.model.SoalModel;
import com.example.demo.repository.HasilLatihanRepository;
import com.example.demo.repository.SoalRepository;
import com.example.demo.service.CompilerService;

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
@RequestMapping("/hasillatihan")
public class HasilLatihanController {
    
    @Autowired
    HasilLatihanRepository hasilLatihanRepository;

    @Autowired
    SoalRepository soalRepository;

    @Autowired
    CompilerService service;
    
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
    
    //TODO : CREATE A FINAL RESULT
    @GetMapping("/nilaiakhir/dosen/{id}")
    public ResponseEntity<GetScore> getNilaiAkhirByIdDosen(HttpServletRequest request,@PathVariable(value = "id") int idDosen){
		final String requestTokenHeader = request.getHeader("Authorization");
        int idMahasiswa =Integer.parseInt(jwtTokenUtil.getUserIdFromToken(requestTokenHeader.substring(7)));
        List<HasilLatihanModel> hasilLatihan=hasilLatihanRepository.findHasilLatihanByIdMahasiswaAndDosen2(idMahasiswa, idDosen); 
        if(!hasilLatihan.isEmpty()){
            double pengurangan = 100/hasilLatihan.size();
            double nilai = 100;
            for (HasilLatihanModel hasilLatihanModel : hasilLatihan) {
                if(hasilLatihanModel.getStatus().equalsIgnoreCase("ERROR")){
                    nilai -= pengurangan;
                }
            }
            GetScore dataScore = new GetScore(hasilLatihan,nilai);
    
            return ResponseEntity.ok(dataScore);
        }else{
            GetScore dataScore = new GetScore(hasilLatihan,0);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<HasilLatihanModel>> getAll(HttpServletRequest request){
		final String requestTokenHeader = request.getHeader("Authorization");
        int idDosen =Integer.parseInt(jwtTokenUtil.getUserIdFromToken(requestTokenHeader.substring(7)));
        return ResponseEntity.ok(hasilLatihanRepository.findHasilLatihanByIdDosen(idDosen));
    }

    @GetMapping("/{idMahasiswa}")
    public ResponseEntity<List<HasilLatihanModel>> getAllByIdMahasiswa(@PathVariable(value="idMahasiswa") int idMahasiswa,HttpServletRequest request){
		final String requestTokenHeader = request.getHeader("Authorization");
        int idDosen =Integer.parseInt(jwtTokenUtil.getUserIdFromToken(requestTokenHeader.substring(7)));
        return ResponseEntity.ok(hasilLatihanRepository.findHasilLatihanByIdMahasiswaAndDosen(idMahasiswa,idDosen));
    }

    @GetMapping("/mahasiswa/")
    public ResponseEntity<List<HasilLatihanModel>> getAllHasilMahasiswa(HttpServletRequest request){
		final String requestTokenHeader = request.getHeader("Authorization");
        int idMahasiswa =Integer.parseInt(jwtTokenUtil.getUserIdFromToken(requestTokenHeader.substring(7)));
        return ResponseEntity.ok(hasilLatihanRepository.findHasilLatihanByIdMahasiswa(idMahasiswa));
    }

    @PostMapping("/")
    public ResponseEntity<String> tambahHasilLatihan(@RequestBody HasilLatihanModel hasil,HttpServletRequest request){
		final String requestTokenHeader = request.getHeader("Authorization");
        hasil.setTanggalSubmit(new Date(new java.util.Date().getTime()));

        SoalModel soal = soalRepository.findById(hasil.getIdSoal().getIdSoal()).get();
        MahasiswaModel mahasiswaModel =new MahasiswaModel();
        mahasiswaModel.setIdMahasiswa(Integer.parseInt(jwtTokenUtil.getUserIdFromToken(requestTokenHeader.substring(7)))); 

        hasil.setIdMahasiswa(mahasiswaModel);
        CompilerModel data = service.getOutput(new CompilerModel(hasil.getHasilCode(),""));
        String[] splitDataOutput = data.getOutput().split("\n"); //pengecekan line akhir menjadi 2 bagian
        String splitDataOutputs = splitDataOutput[0];
        String jawabanMaha = hasil.getJawabanMahasiswa().toString();
        if(splitDataOutputs.contains(jawabanMaha)){
            if(hasil.getJawabanMahasiswa().equalsIgnoreCase(soal.getJawaban())){
                hasil.setStatus("SUCCESS");
                hasil.setKeteranganStatus("Berhasil menjawab sesuai");
            }else{
                hasil.setStatus("ERROR");
                hasil.setKeteranganStatus(data.getOutput());
                // return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{\"message\":\"Hasil salah\"}");
            }
           
        }else{
            hasil.setStatus("ERROR");
            hasil.setKeteranganStatus(data.getOutput());
            // return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body("{\"message\":\""+data.getOutput()+"\"}");
        }
        hasilLatihanRepository.save(hasil);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{\"message\":\"berhasil ditambahkan\"}");
    }

}
