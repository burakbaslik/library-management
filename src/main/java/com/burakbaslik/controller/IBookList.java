package com.burakbaslik.controller;

import com.burakbaslik.dto.book.BookDtoIU;
import org.springframework.web.bind.annotation.PathVariable;

public interface IBookList {
    public BookDtoIU addBook(@PathVariable Long id);
}
