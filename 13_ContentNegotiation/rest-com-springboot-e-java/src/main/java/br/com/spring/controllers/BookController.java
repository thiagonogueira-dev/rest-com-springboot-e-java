package br.com.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.spring.data.vo.v1.BookVO;
import br.com.spring.services.BookServices;
import br.com.spring.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/book/v1")
@Tag(name = "Livros"  ,description = "Endpoint para gerenciar livros")
public class BookController {

	@Autowired
	BookServices service;
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Busca todos os livros", description = "Busca todos os livros",
		tags = {"Livros"},
		responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
				content = {
					@Content(
						mediaType = "application/json",
						array = @ArraySchema(schema = @Schema(implementation = BookVO.class))
					)
				}
			),
			@ApiResponse(description = "Requisição ruim", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro interno do servidor", responseCode = "500", content = @Content),
		}
	)
	public List<BookVO> findAll(){
		return service.findAll();
	}
	
	@GetMapping(value = "/{id}",
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Busca um livro", description = "Busca um livro",
		tags = {"Livros"},
		responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
				content = @Content(schema = @Schema(implementation = BookVO.class))
			),
			@ApiResponse(description = "Sem conteúdo", responseCode = "204", content = @Content),
			@ApiResponse(description = "Requisição ruim", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro interno do servidor", responseCode = "500", content = @Content),
		}
	)
	public BookVO findById(@PathVariable(value = "id") Long id){
		
		return service.findById(id);
	}
	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML },
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Criando um livro, passando uma representação de pessoa em JSON, XML ou YML", 
		description = "Criando um livro",
		tags = {"Livros"},
		responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
				content = @Content(schema = @Schema(implementation = BookVO.class))
			),
			@ApiResponse(description = "Requisição ruim", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Erro interno do servidor", responseCode = "500", content = @Content),
		}
	)
	public ResponseEntity<BookVO> create(@RequestBody BookVO book){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(book));
	}
	
	@PutMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML },
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Modificando um livro, passando uma representação de pessoa em JSON, XML ou YML", 
		description = "Criando um livro",
		tags = {"Livros"},
		responses = {
			@ApiResponse(description = "Modificado", responseCode = "200", 
				content = @Content(schema = @Schema(implementation = BookVO.class))
			),
			@ApiResponse(description = "Requisição ruim", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro interno do servidor", responseCode = "500", content = @Content),
		}
	)
	public BookVO update(@RequestBody BookVO book){
		return service.update(book);
	}
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deletando um livro, passando seu ID como parâmetro na URL", 
		description = "Deletando um livro",
		tags = {"Livros"},
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
