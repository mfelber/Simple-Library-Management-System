package com.alanata.library_management_system.service;

import java.util.List;

import com.alanata.library_management_system.dto.request.CreateBookRequest;
import com.alanata.library_management_system.dto.request.UpdateBookCopyRequest;
import com.alanata.library_management_system.dto.request.UpdateBookRequest;
import com.alanata.library_management_system.dto.response.BookCopyResponse;
import com.alanata.library_management_system.dto.response.BookDetailResponse;
import com.alanata.library_management_system.dto.response.BookResponse;

public interface BookService {

  List<BookResponse> getBooks();

  BookResponse createBook(CreateBookRequest request);

  BookDetailResponse findById(Long bookId);

  BookResponse updateBook(Long bookId, UpdateBookRequest request);

  void deleteBook(Long bookId);

  List<BookCopyResponse> getBookCopies(Long bookId);

  BookCopyResponse addBookCopy(Long bookId);

  BookCopyResponse updateAvailability(Long bookId, Long copyId, UpdateBookCopyRequest request);

}
