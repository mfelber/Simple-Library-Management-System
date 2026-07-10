package com.alanata.library_management_system.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alanata.library_management_system.dto.request.CreateBookRequest;
import com.alanata.library_management_system.dto.request.UpdateBookCopyRequest;
import com.alanata.library_management_system.dto.request.UpdateBookRequest;
import com.alanata.library_management_system.dto.response.BookCopyResponse;
import com.alanata.library_management_system.dto.response.BookDetailResponse;
import com.alanata.library_management_system.dto.response.BookResponse;
import com.alanata.library_management_system.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class LibraryController {

  private final BookService bookService;

  @GetMapping
  public List<BookResponse> getBooks() {
    return bookService.getBooks();
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookDetailResponse> getBookById(@PathVariable Long id) {
    return ResponseEntity.ok(bookService.findById(id));
  }

  @PostMapping
  public ResponseEntity<BookResponse> createBook(
      @Valid @RequestBody CreateBookRequest request
  ) {
    BookResponse response = bookService.createBook(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public BookResponse updateBook(@PathVariable Long id, @RequestBody UpdateBookRequest request) {
    return bookService.updateBook(id, request);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
    bookService.deleteBook(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}/copies")
  public List<BookCopyResponse> getBookCopies(@PathVariable Long id) {
    return bookService.getBookCopies(id);
  }

  @PostMapping("/{id}/copies")
  public BookCopyResponse addCopy(@PathVariable Long id) {
    return bookService.addBookCopy(id);
  }

  @PutMapping("/{id}/copies/{copyId}")
  public BookCopyResponse updateCopyAvailability(@PathVariable Long id, @PathVariable Long copyId, @Valid @RequestBody
      UpdateBookCopyRequest request) {
    return bookService.updateAvailability(id, copyId, request);
  }
}
