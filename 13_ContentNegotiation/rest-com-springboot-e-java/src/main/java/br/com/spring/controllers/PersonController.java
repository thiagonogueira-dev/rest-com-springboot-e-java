package br.com.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.spring.data.vo.v1.PersonVO;
import br.com.spring.services.PersonServices;
import br.com.spring.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "Pessoas", description = "Endpoint para gerenciar pessoas")
public class PersonController {
	
	@Autowired
	private PersonServices service;
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Busca todas as pessoas", description = "Busca todas as pessoas",
		tags = {"Pessoas"},
		responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
				content = {
					@Content(
						mediaType = "application/json",
						array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
					)
				}
			),
			@ApiResponse(description = "Requisição ruim", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro interno do servidor", responseCode = "500", content = @Content),
		}
	)
	public List<PersonVO> findAll(){
		return service.findAll();
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping(value = "/{id}",
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Busca uma pessoa", description = "Busca uma pessoa",
		tags = {"Pessoas"},
		responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
				content = @Content(schema = @Schema(implementation = PersonVO.class))
			),
			@ApiResponse(description = "Sem conteúdo", responseCode = "204", content = @Content),
			@ApiResponse(description = "Requisição ruim", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro interno do servidor", responseCode = "500", content = @Content),
		}
	)
	public PersonVO findById(@PathVariable(value = "id") Long id){
		
		return service.findById(id);
	}
	
	@CrossOrigin(origins = { "http://localhost:8080", "https://erudio.com.br" })
	@PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML },
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Criando uma pessoa, passando uma representação de pessoa em JSON, XML ou YML", 
		description = "Criando uma pessoa",
		tags = {"Pessoas"},
		responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
				content = @Content(schema = @Schema(implementation = PersonVO.class))
			),
			@ApiResponse(description = "Requisição ruim", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Erro interno do servidor", responseCode = "500", content = @Content),
		}
	)
	public ResponseEntity<PersonVO> create(@RequestBody PersonVO person){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(person));
	}
	
	@PutMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML },
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Modificando uma pessoa, passando uma representação de pessoa em JSON, XML ou YML", 
		description = "Criando uma pessoa",
		tags = {"Pessoas"},
		responses = {
			@ApiResponse(description = "Modificado", responseCode = "200", 
				content = @Content(schema = @Schema(implementation = PersonVO.class))
			),
			@ApiResponse(description = "Requisição ruim", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro interno do servidor", responseCode = "500", content = @Content),
		}
	)
	public PersonVO update(@RequestBody PersonVO person){
		return service.update(person);
	}
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deletando uma pessoa, passando seu ID como parâmetro na URL", 
		description = "Deletando uma pessoa",
		tags = {"Pessoas"},
		responses = {
			@ApiResponse(description = "No content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Requisição ruim", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro interno do servidor", responseCode = "500", content = @Content),
		}
	)
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}