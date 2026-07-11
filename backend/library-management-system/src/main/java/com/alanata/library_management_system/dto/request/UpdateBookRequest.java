package com.alanata.library_management_system.dto.request;

import jakarta.validation.constraints.Pattern;

public record UpdateBookRequest(
    String title,
    String author,
    @Pattern(
        regexp = "^(978|979)-\\d{10}$",
        message = "Invalid ISBN format"
    )
    String isbn,
    Integer publishedYear
) {

}
