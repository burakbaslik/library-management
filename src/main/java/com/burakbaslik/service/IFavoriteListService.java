package com.burakbaslik.service;

import com.burakbaslik.dto.favoritelist.FavoriteListBooksDto;
import com.burakbaslik.dto.favoritelist.FavoriteListDto;

public interface IFavoriteListService {

    public FavoriteListDto addBook(Long user_id, Long book_id);
    public FavoriteListDto getFavoriteListBooks(Long user_id);
    public void removeBook(Long user_id, Long book_id);
}
