package br.com.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.com.spring.model.Person;

@Service
public class PersonServices {
	
	private static final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	public List<Person> findAll() {
		
		logger.info("Buscando todas as pessoas!");
		
		List<Person> persons = new ArrayList<Person>();
		for(int i = 0; i < 8; i++) {
			Person person = mockPerson(i);
			persons.add(person);
		}
		
		
		return persons;
	}
	
	public Person findById(String id) {
		
		logger.info("Buscando uma pessoa!");
		
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Thiago");
		person.setLastName("Nogueira");
		person.setAddress("NF");
		person.setGender("Masculino");
		
		return person;
	}
	
	
	public Person create(Person person) {
		logger.info("Criando uma pessoa!");
		
		return person;
	}
	
	public Person update(Person person) {
		logger.info("Atualizando uma pessoa!");
		
		return person;
	}
	
	public void delete(String id) {
		logger.info("Deletando uma pessoa!");
	}
	
	private Person mockPerson(int i) {
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Primeiro nome: " + i);
		person.setLastName("Último nome: " + i);
		person.setAddress("Algum lugar");
		person.setGender(i % 2 == 0 ? "Masculino" : "Feminino");
		
		return person;
	}
}
