package com.burakbaslik.controller;

import com.burakbaslik.dto.book.BookDto;
import com.burakbaslik.dto.book.BookDtoIU;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IBookController {
    public BookDto createBook(BookDtoIU bookDto);
    public List<BookDto> getAllBooks();
    public BookDtoIU findBookById(@PathVariable Long bookId);
    public BookDto editBook(@PathVariable Long id ,@RequestBody BookDtoIU bookDto);
    public void removeBookById(@PathVariable Long bookId);
}
