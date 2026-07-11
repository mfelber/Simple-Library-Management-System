package com.alanata.library_management_system.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public record UpdateBookRequest(
    String title,
    String author,
    @Pattern(
        regexp = "^(978|979)-\\d{10}$",
        message = "Invalid ISBN format"
    )
    String isbn,
    @Min(value = 1000, message = "Invalid published year")
    @Max(value = 2026, message = "Published year cannot be in the future")
    Integer publishedYear
) {

}
