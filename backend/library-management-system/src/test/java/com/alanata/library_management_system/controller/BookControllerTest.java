package com.alanata.library_management_system.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.alanata.library_management_system.dto.request.CreateBookRequest;
import com.alanata.library_management_system.dto.request.UpdateBookRequest;
import com.alanata.library_management_system.dto.response.BookDetailResponse;
import com.alanata.library_management_system.dto.response.BookResponse;
import com.alanata.library_management_system.service.BookService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(BookController.class)
public class BookControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private BookService bookService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void shouldReturnAllBooks() throws Exception {
    BookResponse response = BookResponse.builder()
        .id(1L)
        .title("test")
        .author("Martin S.")
        .isbn("978-0132350884")
        .publishedYear(2002)
        .build();

    when(bookService.getBooks()).thenReturn(List.of(response));

    mockMvc.perform(get("/api/books"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].title").value("test"))
        .andExpect(jsonPath("$[0].author").value("Martin S."))
        .andExpect(jsonPath("$[0].isbn").value("978-0132350884"))
        .andExpect(jsonPath("$[0].publishedYear").value(2002));

    verify(bookService).getBooks();
  }

  @Test
  void shouldReturnBookById() throws Exception {
    BookDetailResponse response = BookDetailResponse.builder()
        .id(1L)
        .title("test")
        .author("Martin S.")
        .isbn("978-0132350884")
        .publishedYear(2002)
        .copies(List.of())
        .build();

    when(bookService.findById(1L)).thenReturn(response);

    mockMvc.perform(get("/api/books/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.title").value("test"));

    verify(bookService).findById(1L);
  }

  @Test
  void shouldCreateBook() throws Exception {
    CreateBookRequest request = new CreateBookRequest(
        "test",
        "Martin S.",
        "978-0132350884",
        2002
    );

    BookResponse response = BookResponse.builder()
        .id(1L)
        .title("test")
        .author("Martin S.")
        .isbn("978-0132350884")
        .publishedYear(2002)
        .build();

    when(bookService.createBook(any(CreateBookRequest.class))).thenReturn(response);

    mockMvc.perform(
            post("/api/books").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.title").value("test"));

    verify(bookService).createBook(any(CreateBookRequest.class));
  }

  @Test
  void shouldDeleteBook() throws Exception {
    doNothing().when(bookService).deleteBook(1L);
    mockMvc.perform(delete("/api/books/1")).andExpect(status().isNoContent());
  }

  @Test
  void shouldUpdateBook() throws Exception {
    UpdateBookRequest request = new UpdateBookRequest(
        "updated test book",
        null,
        null,
        null
    );

    BookResponse response = BookResponse.builder()
        .id(1L)
        .title("updated test book")
        .author("Martin S.")
        .isbn("978-0132350884")
        .publishedYear(2002)
        .build();

    when(bookService.updateBook(eq(1L), any(UpdateBookRequest.class))).thenReturn(response);

    mockMvc.perform(put("/api/books/1").contentType(MediaType.APPLICATION_JSON).content(
            objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("updated test book"));

    verify(bookService).updateBook(eq(1L), any(UpdateBookRequest.class));
  }

}
