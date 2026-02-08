package com.burakbaslik.dto.author;

import lombok.Builder;

@Builder
public record RegisterRequest(
        String name,
        String username,
        String password
) {
}


