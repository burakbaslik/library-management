package com.burakbaslik.dto.favoritelist;

import lombok.Data;

@Data
public class AddBookRequestDto {
    private Long userId;
    private Long bookId;
}
