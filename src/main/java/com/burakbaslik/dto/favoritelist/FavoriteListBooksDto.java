package com.burakbaslik.dto.favoritelist;

import com.burakbaslik.model.Book;
import com.burakbaslik.model.FavoriteList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteListBooksDto {
    List<Book> book;
    Long favoriteListId;
}
