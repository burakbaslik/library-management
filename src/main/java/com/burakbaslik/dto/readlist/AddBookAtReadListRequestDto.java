package com.burakbaslik.dto.readlist;

import com.burakbaslik.model.Book;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
@Data
public class AddBookAtReadListRequestDto {
    private Long userId;
    private Long bookId;
}
