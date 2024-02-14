package br.com.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.spring.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
