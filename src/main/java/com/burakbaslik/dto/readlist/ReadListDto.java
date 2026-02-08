package com.burakbaslik.dto.readlist;

import com.burakbaslik.dto.book.BookDto;
import com.burakbaslik.model.Book;
import com.burakbaslik.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadListDto {
    private User user;
    private List<BookDto> books;
}
