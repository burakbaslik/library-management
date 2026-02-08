package com.burakbaslik.controller;

import com.burakbaslik.dto.author.AuthorDto;
import com.burakbaslik.dto.author.AuthorDtoIU;
import com.burakbaslik.model.Author;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IAuthorController {
    public AuthorDto createAuthor(@RequestBody AuthorDto authorDto);
    public List<AuthorDto> getAllAuthor();
    public AuthorDto getAuthorById(@PathVariable(name = "id") Long id);
    public AuthorDtoIU editAuthor(@PathVariable(name = "id") Long id, @RequestBody AuthorDtoIU authorDto);
    public void deleteAuthor(@PathVariable Long id);
}
