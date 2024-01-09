package br.com.spring.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.spring.data.vo.v1.PersonVO;
import br.com.spring.data.vo.v2.PersonVOV2;
import br.com.spring.exceptions.ResourceNotFoundException;
import br.com.spring.mapper.DozerMapper;
import br.com.spring.mapper.custom.PersonMapper;
import br.com.spring.model.Person;
import br.com.spring.repositories.PersonRepository;

@Service
public class PersonServices {
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository repository;
	@Autowired
	PersonMapper mapper;
	
	public List<PersonVO> findAll() {
		
		logger.info("Buscando todas as pessoas!");		
		
		return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
	}
	
	public PersonVO findById(Long id) {
		
		logger.info("Buscando uma pessoa!");
		
		Person entity = repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Nenhum dado encontrado"));
		
		return DozerMapper.parseObject(entity, PersonVO.class);

	}
	
	
	public PersonVO create(PersonVO person) {
		logger.info("Criando uma pessoa!");
		
		Person entity = DozerMapper.parseObject(person, Person.class);
		PersonVO vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		
		return vo;
		
	}
	
	public PersonVOV2 createV2(PersonVOV2 person) {
		logger.info("Criando uma pessoa!");
		
		Person entity = mapper.convertVoToEntity(person);
		PersonVOV2 vo = mapper.convertEntityToVo(repository.save(entity));
		
		return vo;
		
	}
	
	public PersonVO update(PersonVO person) {
		logger.info("Atualizando uma pessoa!");
		
		Person entity = repository.findById(person.getId())
		.orElseThrow(() -> new ResourceNotFoundException("Nenhum dado encontrado"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		PersonVO vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		
		return vo;
	}
	
	public void delete(Long id) {
		logger.info("Deletando uma pessoa!");
		
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhum dado encontrado"));
		
		repository.delete(entity);
		
	}

}
