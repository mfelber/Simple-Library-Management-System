package com.alanata.library_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alanata.library_management_system.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

  boolean existsBookByIsbn(String isbn);

  boolean existsBookByTitle(String title);

}
