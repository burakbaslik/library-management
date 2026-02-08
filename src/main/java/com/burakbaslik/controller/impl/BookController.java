package com.burakbaslik.controller.impl;

import com.burakbaslik.controller.IBookController;
import com.burakbaslik.dto.book.BookDto;
import com.burakbaslik.dto.book.BookDtoIU;
import com.burakbaslik.service.IBookService;
import com.burakbaslik.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController implements IBookController {
    @Autowired
    private IBookService bookService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Override
    public BookDto createBook(@RequestBody BookDtoIU bookDto) {
        return bookService.createBook(bookDto);
    }

    @GetMapping
    @Override
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    @Override
    public BookDtoIU findBookById(@PathVariable(name = "id") Long bookId) {
        return bookService.findBookById(bookId);
    }

    @PutMapping("/{id}")
    @Override
    public BookDto editBook(@PathVariable(name = "id") Long id, @RequestBody BookDtoIU bookDto) {
        return bookService.editBook(id, bookDto);
    }

    @DeleteMapping("/{id}")
    @Override
    public void removeBookById(@PathVariable(name = "id") Long id) {
        bookService.removeBookById(id);
    }
}
