package com.burakbaslik.service.impl;

import com.burakbaslik.dto.book.BookDto;
import com.burakbaslik.dto.favoritelist.FavoriteListBooksDto;
import com.burakbaslik.dto.favoritelist.FavoriteListDto;
import com.burakbaslik.model.Book;
import com.burakbaslik.model.FavoriteList;
import com.burakbaslik.model.User;
import com.burakbaslik.repository.BookRepository;
import com.burakbaslik.repository.FavoriteListBooksRepository;
import com.burakbaslik.repository.FavoriteListRepository;
import com.burakbaslik.repository.UserRepository;
import com.burakbaslik.service.IFavoriteListService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteListServiceImpl implements IFavoriteListService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FavoriteListBooksRepository favoriteListBooksRepository;
    @Autowired
    private FavoriteListRepository favoriteListRepository;



    public FavoriteListDto addBook(Long user_id, Long book_id) {

        Book book = bookRepository.findById(book_id)
                .orElseThrow(() -> new EntityNotFoundException("Kitap bulunamadı: " + book_id));
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new EntityNotFoundException("Kullanıcı bulunamadı: " + user_id));

        FavoriteList favlist = favoriteListRepository.findByUser(user)
                .orElseGet(() -> {
                    FavoriteList newFavoriteList = new FavoriteList();
                    newFavoriteList.setUser(user);
                    newFavoriteList.setBooks(new HashSet<>());
                    return newFavoriteList;
                });
        favlist.getBooks().add(book);
        FavoriteList saved = favoriteListRepository.save(favlist);

        return mapToFavoriteListDto(saved) ;
    }

    @Override
    public FavoriteListDto getFavoriteListBooks(Long user_id) {

        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new EntityNotFoundException("Kullanıcı bulunamadı: " + user_id));

        Optional<FavoriteList> favoriteList = favoriteListRepository.findByUser(user);

        return mapToFavoriteListDto(favoriteList.orElse(null));
    }

    @Override
    public void removeBook(Long user_id, Long book_id) {
        Book book = bookRepository.findById(book_id)
                .orElseThrow(() -> new EntityNotFoundException("Kitap bulunamadı: " + book_id));
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new EntityNotFoundException("Kullanıcı bulunamadı: " + user_id));
        FavoriteList favoriteList = favoriteListRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Kullanıcıya ait favorite list bulunamadı" + user_id));
        boolean remove = favoriteList.getBooks().remove(book);
        System.out.println(remove);

        favoriteListRepository.save(favoriteList);

    }

    private FavoriteListDto mapToFavoriteListDto(FavoriteList favoriteList) {

        FavoriteListDto dto = new FavoriteListDto();
        dto.setId(favoriteList.getId());
        dto.setUserId(favoriteList.getUser().getId());


        List<BookDto> bookDtos = favoriteList.getBooks().stream()
                .map(this::mapToBookDto)
                .collect(Collectors.toList());

        dto.setBooks(bookDtos);

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
