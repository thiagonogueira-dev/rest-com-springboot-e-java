package br.com.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.spring.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
