package com.alanata.library_management_system.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateBookRequest(
    @NotBlank(message = "Title is required")
    String title,

    @NotBlank(message = "Author is required")
    String author,

    @NotBlank(message = "ISBN is required")
    @Pattern(
        regexp = "^(978|979)-\\d{10}$",
        message = "Invalid ISBN format"
    )
    String isbn,

    @NotNull(message = "Published year is required")
    @Min(value = 1000, message = "Invalid published year")
    @Max(value = 2026, message = "Published year cannot be in the future")
    Integer publishedYear
) {

}
