package com.burakbaslik.service;

import com.burakbaslik.dto.book.BookDto;
import com.burakbaslik.dto.book.BookDtoIU;

import java.util.List;

public interface IBookService {
    public BookDto createBook(BookDtoIU bookDto);
    public List<BookDto> getAllBooks();
    public BookDtoIU findBookById(Long id);
    public BookDto editBook(Long id, BookDtoIU bookDto);
    public void removeBookById(Long id);
}
