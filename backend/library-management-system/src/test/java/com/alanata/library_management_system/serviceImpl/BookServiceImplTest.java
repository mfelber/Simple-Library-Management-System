package com.alanata.library_management_system.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.alanata.library_management_system.dto.request.UpdateBookCopyRequest;
import com.alanata.library_management_system.dto.response.BookCopyResponse;
import com.alanata.library_management_system.dto.response.BookDetailResponse;
import com.alanata.library_management_system.dto.response.BookResponse;
import com.alanata.library_management_system.exception.ResourceNotFoundException;
import com.alanata.library_management_system.mapper.BookMapper;
import com.alanata.library_management_system.model.Book;
import com.alanata.library_management_system.model.BookCopy;
import com.alanata.library_management_system.repository.BookCopyRepository;
import com.alanata.library_management_system.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

  @Mock
  private BookRepository bookRepository;

  @Mock
  private BookCopyRepository bookCopyRepository;

  @Mock
  private BookMapper bookMapper;

  @InjectMocks
  private BookServiceImpl bookService;

  @Test
  void shouldReturnAllBooks() {
    Book book = Book.builder()
        .Id(1L)
        .title("test")
        .author("Martin S.")
        .isbn("978-0132350884")
        .publishedYear(2002)
        .build();

    BookResponse response = BookResponse.builder()
        .id(1L)
        .title("test")
        .author("Martin S.")
        .isbn("978-0132350884")
        .publishedYear(2002)
        .build();

    Pageable pageable = PageRequest.of(0, 10);

    when(bookRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(book)));
    when(bookMapper.toResponse(book)).thenReturn(response);

    Page<BookResponse> result = bookService.getBooks(pageable);

    assertEquals(1, result.getContent().size());
    assertEquals("test", result.getContent().get(0).title());

    verify(bookRepository).findAll(pageable);
    verify(bookMapper).toResponse(book);
  }

  @Test
  void shouldReturnBookById() {

    Book book = Book.builder()
        .Id(1L)
        .title("test")
        .author("Martin S.")
        .isbn("978-0132350884")
        .publishedYear(2002)
        .build();

    BookDetailResponse response = BookDetailResponse.builder()
        .id(1L)
        .title("test")
        .author("Martin S.")
        .isbn("978-0132350884")
        .publishedYear(2002)
        .build();

    when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
    when(bookMapper.toDetailedResponse(book)).thenReturn(response);

    BookDetailResponse result = bookService.findById(1L);

    assertEquals(1L, result.id());
    assertEquals("test", result.title());
  }

  @Test
  void shouldThrowExceptionWhenBookDoesNotExists() {
    when(bookRepository.findById(1000L)).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> bookService.findById(1000L));
  }

  @Test
  void shouldDeleteBook() {
    Book book = Book.builder()
        .Id(1L)
        .title("test")
        .author("Martin S.")
        .isbn("978-0132350884")
        .publishedYear(2002)
        .build();

    when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

    bookService.deleteBook(1L);

    verify(bookRepository).delete(book);
  }

  @Test
  void shouldAddCopy() {
    Book book = Book.builder()
        .Id(1L)
        .title("test")
        .author("Martin S.")
        .isbn("978-0132350884")
        .publishedYear(2002)
        .build();

    BookCopy copy = BookCopy.builder().Id(1L).book(book).available(true).build();

    BookCopyResponse response = BookCopyResponse.builder().id(1L).available(true).build();

    when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
    when(bookCopyRepository.save(any(BookCopy.class))).thenReturn(copy);
    when(bookMapper.toBookCopiesResponse(copy)).thenReturn(response);

    BookCopyResponse result = bookService.addBookCopy(1L);

    assertTrue(result.available());

    verify(bookCopyRepository).save(any(BookCopy.class));
  }

  @Test
  void shouldUpdateBookCopyAvailability() {
    Book book = Book.builder()
        .Id(1L)
        .title("test")
        .author("Martin S.")
        .isbn("978-0132350884")
        .publishedYear(2002)
        .build();

    BookCopy copy = BookCopy.builder().Id(5L).book(book).available(true).build();

    UpdateBookCopyRequest request = new UpdateBookCopyRequest(false);

    BookCopyResponse response = BookCopyResponse.builder().id(5L).available(false).build();

    when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
    when(bookCopyRepository.findById(5L)).thenReturn(Optional.of(copy));
    when(bookCopyRepository.save(copy)).thenReturn(copy);
    when(bookMapper.toBookCopiesResponse(copy)).thenReturn(response);

    BookCopyResponse result = bookService.updateAvailability(1l, 5L, request);

    assertFalse(result.available());

    verify(bookCopyRepository).save(copy);
  }

}
