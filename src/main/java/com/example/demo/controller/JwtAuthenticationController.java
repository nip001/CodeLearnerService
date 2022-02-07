package com.example.demo.controller;


import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.DosenModel;
import com.example.demo.model.JwtRequest;
import com.example.demo.model.JwtResponse;
import com.example.demo.model.MahasiswaModel;
import com.example.demo.service.JwtUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class JwtAuthenticationController {
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtTokenUtil tokenUtil;

	@Autowired
	private JwtUserDetailsService udService;
	
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest,@RequestParam(value = "role") String role) throws Exception {
		udService.setRoleUser(role);
		int dataId = 0;
		String nama,fotoUser;
		if(role.equalsIgnoreCase("dosen")){
			DosenModel dosen = new DosenModel();
			dosen = udService.getDosenDetails(authenticationRequest.getUsername());
			dataId =  dosen.getIdDosen();
			nama = dosen.getNamaDosen();
			fotoUser = dosen.getFotoDosen();
		}else{
			MahasiswaModel mahasiswa = new MahasiswaModel();
			mahasiswa = udService.getMahasiswaDetails(authenticationRequest.getUsername());
			dataId = mahasiswa.getIdMahasiswa();
			nama = mahasiswa.getNamaMahasiswa();
			fotoUser = mahasiswa.getFotoMahasiswa();
		}
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = udService.loadUserByUsername(authenticationRequest.getUsername());
		
		final String token = tokenUtil.generateToken(userDetails,role,dataId);

		return ResponseEntity.ok(new JwtResponse(token, nama, fotoUser, dataId));
	}
	
	
	private void authenticate(String username,String password) throws Exception {
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		}catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
