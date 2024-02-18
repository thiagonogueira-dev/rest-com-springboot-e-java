package br.com.spring.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.spring.controllers.PersonController;
import br.com.spring.data.vo.v1.PersonVO;
import br.com.spring.exceptions.RequiredObjectIsNullException;
import br.com.spring.exceptions.ResourceNotFoundException;
import br.com.spring.mapper.DozerMapper;
import br.com.spring.model.Person;
import br.com.spring.repositories.PersonRepository;
import jakarta.transaction.Transactional;

@Service
public class PersonServices {
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository repository;
	
	@Autowired
	PagedResourcesAssembler<PersonVO> assembler;
	
	public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable) {
		
		logger.info("Buscando todas as pessoas!");
		
		var personPage = repository.findAll(pageable);
		
		var personVosPage = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));
		personVosPage.map(p -> p.add(
				linkTo(methodOn(PersonController.class).
						findById(p.getKey())).withSelfRel()));
		
		Link link = linkTo(
				methodOn(PersonController.class)
					.findAll(pageable.getPageNumber(), pageable.getPageSize(), 
					"asc")).withSelfRel();
		
		return assembler.toModel(personVosPage, link);
	}
	
	public PagedModel<EntityModel<PersonVO>> findPersonByName(String firstname, Pageable pageable) {
		
		logger.info("Buscando todas por um nome específico!");
		
		var personPage = repository.findPersonByName(firstname, pageable);
		
		var personVosPage = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));
		personVosPage.map(p -> p.add(
				linkTo(methodOn(PersonController.class).
						findById(p.getKey())).withSelfRel()));
		
		Link link = linkTo(
				methodOn(PersonController.class)
				.findAll(pageable.getPageNumber(), pageable.getPageSize(), 
						"asc")).withSelfRel();
		
		return assembler.toModel(personVosPage, link);
	}
	
	public PersonVO findById(Long id) {
		
		logger.info("Buscando uma pessoa!");
		
		Person entity = repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Nenhum dado encontrado"));
		
		PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;

	}
	
	public PersonVO create(PersonVO person) {
		
		if(person == null)
			throw new RequiredObjectIsNullException();
		
		logger.info("Criando uma pessoa!");
		
		Person entity = DozerMapper.parseObject(person, Person.class);
		PersonVO vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
		
	}
	
	public PersonVO update(PersonVO person) {
		
		if(person == null)
			throw new RequiredObjectIsNullException();
		
		logger.info("Atualizando uma pessoa!");
		
		Person entity = repository.findById(person.getKey())
			.orElseThrow(() -> new ResourceNotFoundException("Nenhum dado encontrado"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		PersonVO vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	@Transactional
	public PersonVO disablePerson(Long id) {
		
		logger.info("Desabilitando uma pessoa!");
		
		repository.disablePerson(id);
		
		Person entity = repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Nenhum dado encontrado"));
		
		PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;

	}
	
	public void delete(Long id) {
		logger.info("Deletando uma pessoa!");
		
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhum dado encontrado"));
		
		repository.delete(entity);
		
	}

}
