package com.burakbaslik.controller;

import com.burakbaslik.dto.favoritelist.AddBookRequestDto;
import com.burakbaslik.dto.readlist.AddBookAtReadListRequestDto;
import com.burakbaslik.dto.readlist.ReadListDtoIU;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


public interface IReadListController {
    public ReadListDtoIU addBook(@RequestBody AddBookAtReadListRequestDto requestDto);
    public ReadListDtoIU getAllBooks(@PathVariable(name = "id") Long user_id);
    public void deleteBook(@PathVariable("user-id") Long user_id, @PathVariable(name = "book-id") Long book_id);

}
