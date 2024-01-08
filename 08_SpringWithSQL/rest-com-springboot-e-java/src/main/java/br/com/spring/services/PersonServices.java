package br.com.spring.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.spring.exceptions.ResourceNotFoundException;
import br.com.spring.model.Person;
import br.com.spring.repositories.PersonRepository;

@Service
public class PersonServices {
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository repository;
	
	public List<Person> findAll() {
		
		logger.info("Buscando todas as pessoas!");		
		
		return repository.findAll();
	}
	
	public Person findById(Long id) {
		
		logger.info("Buscando uma pessoa!");
		
		return repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Nenhum dado encontrado"));
	}
	
	
	public Person create(Person person) {
		logger.info("Criando uma pessoa!");
		
		return repository.save(person);
	}
	
	public Person update(Person person) {
		logger.info("Atualizando uma pessoa!");
		
		Person entity = repository.findById(person.getId())
		.orElseThrow(() -> new ResourceNotFoundException("Nenhum dado encontrado"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		return repository.save(entity);
	}
	
	public void delete(Long id) {
		logger.info("Deletando uma pessoa!");
		
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhum dado encontrado"));
		
		repository.delete(entity);
		
	}

}
