package com.alanata.library_management_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alanata.library_management_system.model.BookCopy;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {

  List<BookCopy> findByBookId(Long bookId);

}
