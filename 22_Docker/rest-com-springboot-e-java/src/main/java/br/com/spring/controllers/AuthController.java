package br.com.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.spring.data.vo.v1.security.AccountCredentialsVO;
import br.com.spring.services.AuthServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Endpoint de autenticação")
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthServices authServices;
	
	@SuppressWarnings("rawtypes")
	@Operation(summary = "Autentica um usuário e retorna um token")
	@PostMapping(value = "/signin")
	public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {
		
		if(checkIfParamsIsNotNull(data)) {
			return ResponseEntity
					.status(HttpStatus.FORBIDDEN)
					.body("Requisição inválida");
		}
		
		var token = authServices.siginin(data);
		if(token == null) {
			return ResponseEntity
					.status(HttpStatus.FORBIDDEN)
					.body("Requisição inválida");
		}
		
		return token;
	}
	
	@SuppressWarnings("rawtypes")
	@Operation(summary = "Atualiza o token de um usuário authenticado e retorna um token")
	@PutMapping(value = "/refresh/{username}")
	public ResponseEntity refreshToken(@PathVariable("username") String username, 
			@RequestHeader("Authorization") String refreshToken) {
		
		if(checkIfParamsIsNotNull(username, refreshToken)) {
			return ResponseEntity
					.status(HttpStatus.FORBIDDEN)
					.body("Requisição inválida");
		}
		
		var token = authServices.refreshToken(username, refreshToken);
		if(token == null) {
			return ResponseEntity
					.status(HttpStatus.FORBIDDEN)
					.body("Requisição inválida");
		}
		
		return token;
	}

	private boolean checkIfParamsIsNotNull(String username, String refreshToken) {
		return refreshToken == null || refreshToken.isBlank() ||
				username == null || username.isBlank();
	}

	private boolean checkIfParamsIsNotNull(AccountCredentialsVO data) {
		return data == null 
				|| data.getUsername() == null || data.getUsername().isBlank()
				|| data.getPassword() == null || data.getPassword().isBlank();
	}
}
