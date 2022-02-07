package com.example.demo.service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.DosenModel;
import com.example.demo.model.MahasiswaModel;
import com.example.demo.repository.DosenRepository;
import com.example.demo.repository.MahasiswaRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	String roleUser;

	@Autowired
	DosenRepository dosenRepo;

	@Autowired
	MahasiswaRepository mahasiswaRepo;
	
	@Autowired
	PasswordEncoder passEncode;

	DosenModel dosen;

	MahasiswaModel mahasiswa;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		List<SimpleGrantedAuthority> roles = new ArrayList<>();
        
		if(roleUser.equalsIgnoreCase("dosen")){
			if(dosen != null){
				roles = Arrays.asList(new SimpleGrantedAuthority("dosen"));
					return new User(dosen.getUsername(), dosen.getPassword(),
							roles);
			}else{
				throw new UsernameNotFoundException("User not found with username: " + username);
			}
		} else{
            
            if(mahasiswa == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }else{
				
				roles = Arrays.asList(new SimpleGrantedAuthority("mahasiswa"));
				return new User(mahasiswa.getUsername(), mahasiswa.getPassword(),
				roles);
			}

        }
		
	}

	public void setRoleUser(String role){
		this.roleUser = role;
	}

	public DosenModel getDosenDetails(String username){
		this.dosen =dosenRepo.findByUsername(username) ;
		return dosen;
	}

	public MahasiswaModel getMahasiswaDetails(String username){
		this.mahasiswa =mahasiswaRepo.findByUsername(username) ;
		return mahasiswa;
	}


}
