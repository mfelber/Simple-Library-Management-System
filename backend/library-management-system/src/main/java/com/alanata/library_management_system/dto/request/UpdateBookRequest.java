package com.alanata.library_management_system.dto.request;

public record UpdateBookRequest(
    String title,
    String author,
    String isbn,
    Integer publishedYear
) {

}
