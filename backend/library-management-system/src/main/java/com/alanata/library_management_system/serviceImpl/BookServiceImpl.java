package com.alanata.library_management_system.serviceImpl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alanata.library_management_system.dto.request.CreateBookRequest;
import com.alanata.library_management_system.dto.request.UpdateBookCopyRequest;
import com.alanata.library_management_system.dto.request.UpdateBookRequest;
import com.alanata.library_management_system.dto.response.BookCopyResponse;
import com.alanata.library_management_system.dto.response.BookDetailResponse;
import com.alanata.library_management_system.dto.response.BookResponse;
import com.alanata.library_management_system.exception.ResourceAlreadyExistsException;
import com.alanata.library_management_system.exception.ResourceNotFoundException;
import com.alanata.library_management_system.mapper.BookMapper;
import com.alanata.library_management_system.model.Book;
import com.alanata.library_management_system.model.BookCopy;
import com.alanata.library_management_system.repository.BookCopyRepository;
import com.alanata.library_management_system.repository.BookRepository;
import com.alanata.library_management_system.service.BookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

  private final BookMapper bookMapper;

  private final BookCopyRepository bookCopyRepository;

  @Override
  public Page<BookResponse> getBooks(Pageable pageable) {
    return bookRepository.findAll(pageable).map(bookMapper::toResponse);
  }

  @Override
  public BookResponse createBook(final CreateBookRequest request) {
    Book book = bookMapper.toEntity(request);
    if (bookRepository.existsBookByIsbn(request.isbn())) {
      throw new ResourceAlreadyExistsException("Book with ISBN '" + request.isbn() + "' already exists.");
    }
    if (bookRepository.existsBookByTitle(request.title())) {
      throw new ResourceAlreadyExistsException("Book with title '" + request.title() + "' already exists.");
    }
    Book savedBook = bookRepository.save(book);
    return bookMapper.toResponse(savedBook);
  }

  @Override
  public BookDetailResponse findById(final Long bookId) {
    return bookRepository.findById(bookId)
        .map(bookMapper::toDetailedResponse)
        .orElseThrow(() -> new ResourceNotFoundException("Book with id: " + bookId + " was not found"));
  }

  @Override
  public BookResponse updateBook(final Long bookId, UpdateBookRequest request) {
    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new ResourceNotFoundException("Book with id: " + bookId + " was not found"));
    if (bookRepository.existsBookByIsbn(request.isbn())) {
      throw new ResourceAlreadyExistsException("Book with ISBN '" + request.isbn() + "' already exists.");
    }
    if (bookRepository.existsBookByTitle(request.title())) {
      throw new ResourceAlreadyExistsException("Book with title '" + request.title() + "' already exists.");
    }
    bookMapper.update(book, request);
    bookRepository.save(book);
    return bookMapper.toResponse(book);
  }

  @Override
  public void deleteBook(final Long bookId) {
    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new ResourceNotFoundException("Book with id: " + bookId + " was not found"));
    bookRepository.delete(book);
  }

  @Override
  public List<BookCopyResponse> getBookCopies(final Long bookId) {
    bookRepository.findById(bookId)
        .orElseThrow(() -> new ResourceNotFoundException("Book with id: " + bookId + " was not found"));

    return bookCopyRepository.findByBookId(bookId).stream().map(bookMapper::toBookCopiesResponse).toList();
  }

  @Override
  public BookCopyResponse addBookCopy(final Long bookId) {
    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new ResourceNotFoundException("Book with id: " + bookId + " was not found"));

    BookCopy bookCopy = BookCopy.builder().book(book).available(true).build();

    bookCopy = bookCopyRepository.save(bookCopy);
    return bookMapper.toBookCopiesResponse(bookCopy);
  }

  @Override
  public BookCopyResponse updateAvailability(final Long bookId, final Long copyId, UpdateBookCopyRequest request) {
    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new ResourceNotFoundException("Book with id: " + bookId + " was not found"));

    BookCopy copy = bookCopyRepository.findById(copyId)
        .orElseThrow(() -> new ResourceNotFoundException("Copy with id: " + copyId + " was not found"));

    if (!copy.getBook().getId().equals(book.getId())) {
      throw new ResourceNotFoundException("Copy does not belong to this book");
    }

    copy.setAvailable(request.available());

    BookCopy updatedCopy = bookCopyRepository.save(copy);

    return bookMapper.toBookCopiesResponse(updatedCopy);
  }

}
