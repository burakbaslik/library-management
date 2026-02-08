package com.burakbaslik.controller.impl;

import com.burakbaslik.controller.IFavoriteList;
import com.burakbaslik.dto.favoritelist.AddBookRequestDto;
import com.burakbaslik.dto.favoritelist.FavoriteListDto;
import com.burakbaslik.service.impl.FavoriteListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorite-list")
public class FavoriteListController implements IFavoriteList {
    @Autowired
    private FavoriteListServiceImpl favoriteListServiceImpl;

    @PostMapping
    @Override
    public FavoriteListDto addBook(@RequestBody AddBookRequestDto request) {
        return favoriteListServiceImpl.addBook(request.getUserId(),  request.getBookId());
    }

    @GetMapping("/{id}")
    @Override
    public FavoriteListDto getFavoriteListBooks(@PathVariable(name = "id") Long user_id) {
        return favoriteListServiceImpl.getFavoriteListBooks(user_id);
    }

    @DeleteMapping("/{user-id}/{book-id}")
    @Override
    public void removeBook(@PathVariable(name = "user-id") Long user_id, @PathVariable(name = "book-id") Long book_id) {
        favoriteListServiceImpl.removeBook(user_id, book_id);
    }


}
