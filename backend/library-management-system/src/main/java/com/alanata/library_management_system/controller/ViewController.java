package com.alanata.library_management_system.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.alanata.library_management_system.dto.response.BookDetailResponse;
import com.alanata.library_management_system.service.BookService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ViewController {

  private final BookService bookService;

  @GetMapping("/")
  public String showBooks(Model model) {

    List<BookDetailResponse> books =
        bookService.getBooks(PageRequest.of(0,100))
            .stream()
            .map(book -> bookService.findById(book.id()))
            .toList();

    model.addAttribute("books", books);

    return "index";
  }

}
