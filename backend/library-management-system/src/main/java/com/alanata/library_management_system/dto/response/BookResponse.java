package com.alanata.library_management_system.dto.response;

import lombok.Builder;

@Builder
public record BookResponse(
    Long id,
    String title,
    String author,
    String isbn,
    Integer publishedYear
){
}
