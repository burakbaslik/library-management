package com.burakbaslik.service.impl;

import com.burakbaslik.dto.book.BookDto;
import com.burakbaslik.dto.book.BookDtoIU;
import com.burakbaslik.model.Author;
import com.burakbaslik.model.Book;
import com.burakbaslik.model.FavoriteList;
import com.burakbaslik.model.ReadList;
import com.burakbaslik.repository.AuthorRepository;
import com.burakbaslik.repository.BookRepository;
import com.burakbaslik.repository.FavoriteListRepository;
import com.burakbaslik.repository.ReadListRepository;
import com.burakbaslik.service.IBookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements IBookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final FavoriteListRepository favoriteListRepository;
    private final ReadListRepository readListRepository;

    @Transactional
    @Override
    public BookDto createBook(BookDtoIU dto) {
        // Author'u repodan bul
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("Author not found: " + dto.getAuthorId()));

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setDescription(dto.getDescription());
        book.setAuthor(author);

        Book saved = bookRepository.save(book);

        BookDto out = new BookDto();
        out.setId(saved.getId());
        out.setTitle(saved.getTitle());
        out.setDescription(saved.getDescription());
        out.setAuthorId(saved.getAuthor().getId());
        return out;
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookDto> out = new ArrayList<>();
        for (Book book : books) {
            BookDto bookDto = new BookDto();
            Author author = book.getAuthor();
            bookDto.setTitle(book.getTitle());
            bookDto.setDescription(book.getDescription());
            bookDto.setAuthorId(author.getId());
            bookDto.setId(book.getId());
            out.add(bookDto);
        }
        return out;
    }

    @Override
    public BookDtoIU findBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not found: " + id));
        Author author = book.getAuthor();
        BookDtoIU bookDtoIU = new BookDtoIU();
        bookDtoIU.setId(book.getId());
        bookDtoIU.setTitle(book.getTitle());
        bookDtoIU.setDescription(book.getDescription());
        bookDtoIU.setAuthorId(author.getId());
        return bookDtoIU;
    }

    @Override
    public BookDto editBook(Long id ,BookDtoIU bookDto) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()){
            Book bookEntity = book.get();
            bookEntity.setTitle(bookDto.getTitle());
            bookEntity.setDescription(bookDto.getDescription());
            Author author = authorRepository.findById(bookDto.getAuthorId()).get();
            bookEntity.setAuthor(author);
            bookRepository.save(bookEntity);

            BookDto bookDtoEntity = new BookDto();
            bookDtoEntity.setId(bookEntity.getId());
            bookDtoEntity.setTitle(bookDto.getTitle());
            bookDtoEntity.setDescription(bookDto.getDescription());
            bookDtoEntity.setAuthorId(author.getId());
            return bookDtoEntity;
        }


        return null;
    }

    @Transactional
    @Override
    public void removeBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not found: " + id));
        for (FavoriteList fl : favoriteListRepository.findAllByBooks_Id(id)) {
            fl.getBooks().remove(book);
        }
        for (ReadList rl : readListRepository.findAllByBooks_Id(id)) {
            rl.getBooks().remove(book);
        }

        bookRepository.delete(book);

    }
}

