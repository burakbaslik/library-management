package com.burakbaslik.dto.readlist;

import com.burakbaslik.dto.book.BookDto;
import com.burakbaslik.model.Book;
import com.burakbaslik.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadListDtoIU {
    private Long id;
    private User user;
    private List<BookDto> books;
}
