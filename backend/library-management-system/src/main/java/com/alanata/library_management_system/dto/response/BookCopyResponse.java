package com.alanata.library_management_system.dto.response;

import lombok.Builder;

@Builder
public record BookCopyResponse(
    Long id,
    Boolean available
) {

}
