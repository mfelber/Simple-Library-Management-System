package com.alanata.library_management_system.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record BookDetailResponse(
    Long id,
    String title,
    String author,
    String isbn,
    Integer publishedYear,
    List<BookCopyResponse> copies
) {

}
