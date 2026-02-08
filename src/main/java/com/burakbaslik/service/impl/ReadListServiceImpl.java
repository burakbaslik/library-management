package com.burakbaslik.service.impl;

import com.burakbaslik.dto.book.BookDto;
import com.burakbaslik.dto.favoritelist.FavoriteListDto;
import com.burakbaslik.dto.readlist.ReadListDto;
import com.burakbaslik.dto.readlist.ReadListDtoIU;
import com.burakbaslik.model.Book;
import com.burakbaslik.model.FavoriteList;
import com.burakbaslik.model.ReadList;
import com.burakbaslik.model.User;
import com.burakbaslik.repository.BookRepository;
import com.burakbaslik.repository.ReadListRepository;
import com.burakbaslik.repository.UserRepository;
import com.burakbaslik.service.IReadListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReadListServiceImpl implements IReadListService {
    @Autowired
    private ReadListRepository readListRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public ReadListDtoIU addBook(Long user_id, Long book_id) {
        Book book = bookRepository.findById(book_id).orElse(null);
        User user = userRepository.findById(user_id).orElse(null);
        ReadList readList = readListRepository.findByUser(user)
                .orElseGet(() -> {
                    ReadList newReadList = new ReadList();
                    newReadList.setUser(user);
                    newReadList.setBooks(new HashSet<>());
                    return newReadList;
                });
        readList.getBooks().add(book);
        ReadList savedReadList = readListRepository.save(readList);

        return mapToReadListDto(savedReadList);
    }

    @Override
    public ReadListDtoIU getAllBooks(Long user_id) {
        User user = userRepository.findById(user_id).orElse(null);
        ReadList readlist = readListRepository.findByUser(user).orElse(null);

        return mapToReadListDto(readlist);
    }

    @Override
    public void deleteBook(Long user_id,Long book_id) {
        User user = userRepository.findById(user_id).orElse(null);
        Book  book = bookRepository.findById(book_id).orElse(null);
        ReadList readlist = readListRepository.findByUser(user).orElse(null);
        readlist.getBooks().remove(book);
        readListRepository.save(readlist);
    }

    private ReadListDtoIU mapToReadListDto(ReadList readlist) {


        ReadListDtoIU dto = new ReadListDtoIU();
        dto.setId(readlist.getId());
        dto.setUser(readlist.getUser());


        List<BookDto> bookdto = readlist.getBooks().stream()
                .map(this::mapToBookDto)
                .collect(Collectors.toList());

        dto.setBooks(bookdto);

        return dto;
    }

    private BookDto mapToBookDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthorId(book.getAuthor().getId());
        dto.setDescription(book.getDescription());
        return dto;
    }



}
