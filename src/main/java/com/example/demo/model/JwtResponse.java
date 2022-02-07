package com.example.demo.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class JwtResponse implements Serializable{
	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final String namaUser;
	private final String fotoUser;
	private final int idUser;
}
