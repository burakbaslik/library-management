package com.burakbaslik.service;

import com.burakbaslik.dto.author.AuthorDto;
import com.burakbaslik.dto.author.AuthorDtoIU;

import java.util.List;

public interface IAuthorService {
    public AuthorDto createAuthor(AuthorDto authorDto);
    public List<AuthorDto> getAllAuthors();
    public AuthorDto findAuthorById(Long id);
    public AuthorDtoIU editAuthor(Long id, AuthorDtoIU authorDto);
    public void deleteAuthor(Long id);

}
