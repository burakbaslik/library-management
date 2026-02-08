package com.burakbaslik.controller;

import com.burakbaslik.dto.favoritelist.AddBookRequestDto;
import com.burakbaslik.dto.favoritelist.FavoriteListBooksDto;
import com.burakbaslik.dto.favoritelist.FavoriteListDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IFavoriteList {
    public FavoriteListDto addBook(@RequestBody AddBookRequestDto request);
    public FavoriteListDto getFavoriteListBooks(@PathVariable(name = "id") Long user_id);
    public void removeBook(@PathVariable (name = "user-id") Long user_id, @PathVariable(name = "book-id") Long book_id);
}
