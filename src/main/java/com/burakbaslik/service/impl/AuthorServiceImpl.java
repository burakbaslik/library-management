package com.burakbaslik.service.impl;

import com.burakbaslik.dto.author.AuthorDto;
import com.burakbaslik.dto.author.AuthorDtoIU;
import com.burakbaslik.model.Author;
import com.burakbaslik.model.Book;
import com.burakbaslik.repository.AuthorRepository;
import com.burakbaslik.repository.BookRepository;
import com.burakbaslik.repository.FavoriteListRepository;
import com.burakbaslik.repository.ReadListRepository;
import com.burakbaslik.service.IAuthorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements IAuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private FavoriteListRepository favoriteListRepository;
    @Autowired
    private ReadListRepository readListRepository;

    @Override
    public AuthorDto createAuthor(AuthorDto authorDto) {
        Author author = new Author();
        BeanUtils.copyProperties(authorDto, author);

        Author saved = authorRepository.save(author);

        // Entity -> DTO
        AuthorDto newAuthorDto = new AuthorDto();
        BeanUtils.copyProperties(saved, newAuthorDto);
        return newAuthorDto;
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorDto> authorDtos = new ArrayList<>();
        for (Author author : authors) {
            AuthorDto authorDto = new AuthorDto();
            BeanUtils.copyProperties(author, authorDto);
            authorDtos.add(authorDto);
        }

        return authorDtos;
    }

    @Override
    public AuthorDto findAuthorById(Long id) {
        AuthorDto authorDto = new AuthorDto();
        Optional<Author> optional = authorRepository.findById(id);
        if(!optional.isPresent()) {
            return null;
        }

        Author  author = optional.get();
        BeanUtils.copyProperties(author, authorDto);

        return authorDto;
    }

    @Override
    public AuthorDtoIU editAuthor(Long id, AuthorDtoIU authorDto) {
        Optional<Author> optional = authorRepository.findById(id);
        if(!optional.isPresent()) {
            return null;
        }
        Author author = optional.get();
        author.setFirstName(authorDto.getFirstName());
        author.setLastName(authorDto.getLastName());

        BeanUtils.copyProperties(authorDto, author);
        Author au =  authorRepository.save(author);
        AuthorDtoIU updatedAuthorDto = new AuthorDtoIU();
        BeanUtils.copyProperties(au, updatedAuthorDto);

        return updatedAuthorDto;
    }

    @Transactional
    @Override
    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id).get();

        List<Long> bookIds = bookRepository.findIdsByAuthorId(id);

        if (!bookIds.isEmpty()) {
            favoriteListRepository.deleteJoinsByBookIds(bookIds);
            readListRepository.deleteJoinsByBookIds(bookIds);
        }

        bookRepository.deleteByAuthorId(id);
        authorRepository.delete(author);

    }


}
