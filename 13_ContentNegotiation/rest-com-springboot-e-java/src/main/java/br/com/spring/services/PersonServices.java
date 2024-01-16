package br.com.spring.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.spring.controllers.PersonController;
import br.com.spring.data.vo.v1.PersonVO;
import br.com.spring.exceptions.RequiredObjectIsNullException;
import br.com.spring.exceptions.ResourceNotFoundException;
import br.com.spring.mapper.DozerMapper;
import br.com.spring.model.Person;
import br.com.spring.repositories.PersonRepository;

@Service
public class PersonServices {
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository repository;
	
	public List<PersonVO> findAll() {
		
		logger.info("Buscando todas as pessoas!");		
		
		List<PersonVO> persons = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
		persons
			.stream()
			.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		return persons;
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
	
	public void delete(Long id) {
		logger.info("Deletando uma pessoa!");
		
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhum dado encontrado"));
		
		repository.delete(entity);
		
	}

}
