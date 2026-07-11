package com.alanata.library_management_system.mapper;

import org.springframework.stereotype.Component;

import com.alanata.library_management_system.dto.request.CreateBookRequest;
import com.alanata.library_management_system.dto.request.UpdateBookRequest;
import com.alanata.library_management_system.dto.response.BookCopyResponse;
import com.alanata.library_management_system.dto.response.BookDetailResponse;
import com.alanata.library_management_system.dto.response.BookResponse;
import com.alanata.library_management_system.model.Book;
import com.alanata.library_management_system.model.BookCopy;

@Component
public class BookMapper {

  public BookResponse toResponse(Book book) {
    return BookResponse.builder()
        .id(book.getId())
        .title(book.getTitle())
        .author(book.getAuthor())
        .isbn(book.getIsbn())
        .publishedYear(book.getPublishedYear())
        .build();
  }

  public BookDetailResponse toDetailedResponse(Book book) {
    return BookDetailResponse.builder()
        .id(book.getId())
        .title(book.getTitle())
        .author(book.getAuthor())
        .isbn(book.getIsbn())
        .publishedYear(book.getPublishedYear())
        .copies(book.getCopies().stream().map(copy -> new BookCopyResponse(copy.getId(), copy.isAvailable())).toList())
        .build();
  }

  public BookCopyResponse toBookCopiesResponse(BookCopy copy) {
    return BookCopyResponse.builder()
        .id(copy.getId())
        .available(copy.isAvailable())
        .build();
  }

  public Book toEntity(CreateBookRequest request) {
    return Book.builder()
        .title(request.title())
        .author(request.author())
        .isbn(request.isbn())
        .publishedYear(request.publishedYear())
        .build();
  }

  public void update(Book book, UpdateBookRequest request) {
    if (request.title() != null) {
      book.setTitle(request.title());
    }
    if (request.author() != null) {
      book.setAuthor(request.author());
    }
    if (request.isbn() != null) {
      book.setIsbn(request.isbn());
    }
    if (request.publishedYear() != null) {
      book.setPublishedYear(Integer.valueOf(String.valueOf(request.publishedYear())));
    }
  }

}
