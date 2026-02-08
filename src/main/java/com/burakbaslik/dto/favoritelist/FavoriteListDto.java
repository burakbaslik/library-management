package com.burakbaslik.dto.favoritelist;

import com.burakbaslik.dto.book.BookDto;
import com.burakbaslik.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteListDto {
    private Long id;
    private Long userId;
    private List<BookDto> books;

   // private Book book;
}
