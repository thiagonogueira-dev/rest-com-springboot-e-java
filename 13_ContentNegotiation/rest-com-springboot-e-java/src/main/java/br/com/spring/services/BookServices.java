package br.com.spring.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.spring.controllers.BookController;
import br.com.spring.data.vo.v1.BookVO;
import br.com.spring.exceptions.RequiredObjectIsNullException;
import br.com.spring.exceptions.ResourceNotFoundException;
import br.com.spring.mapper.DozerMapper;
import br.com.spring.model.Book;
import br.com.spring.repositories.BookRepository;

@Service
public class BookServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	
	@Autowired
	BookRepository repository;
	
	public List<BookVO> findAll() {
		
		logger.info("Buscando todos os livros");
		
		List<BookVO> books = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
		books
			.stream()
			.forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
		return books;
	}
	
	public BookVO findById(Long id) {
		
		logger.info("Buscando um livro");
		
		Book entity = repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Nenhum dado encontrado"));
		
		BookVO book = DozerMapper.parseObject(entity, BookVO.class);
		book.add(linkTo(methodOn(BookController.class).findById(book.getKey())).withSelfRel());

		return book;
	}
	
	public BookVO create(BookVO book) {
		
		if(book == null) {
			throw new RequiredObjectIsNullException();
		}
		
		logger.info("Criando um livro");
		
		Book entity = DozerMapper.parseObject(book, Book.class);
		BookVO vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;

	}
	
	public BookVO update(BookVO book) {
		
		if(book == null)
			throw new RequiredObjectIsNullException();
		
		logger.info("Atualizando um livro");
		
		Book entity = repository.findById(book.getKey())
			.orElseThrow(() -> new ResourceNotFoundException("Nenhum dado encontrado"));
		
		entity.setAuthor(book.getAuthor());
		entity.setTitle(book.getTitle());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		
		BookVO vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) {
		Book entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhum dado encontrado"));
		
		repository.delete(entity);
	}
	
}
