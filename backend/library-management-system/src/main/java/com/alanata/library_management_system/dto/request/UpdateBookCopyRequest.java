package com.alanata.library_management_system.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateBookCopyRequest(
    @NotNull(message = "Availability status is required")
    Boolean available
) {

}
