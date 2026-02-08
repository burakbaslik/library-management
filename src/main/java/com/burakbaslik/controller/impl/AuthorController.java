package com.burakbaslik.controller.impl;

import com.burakbaslik.controller.IAuthorController;
import com.burakbaslik.dto.author.AuthorDto;
import com.burakbaslik.dto.author.AuthorDtoIU;
import com.burakbaslik.service.IAuthorService;
import com.burakbaslik.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController implements IAuthorController {
    @Autowired
    private IAuthorService authorService;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping
    @Override
    public AuthorDto createAuthor(@RequestBody AuthorDto authorDto) {
        return authorService.createAuthor(authorDto);
    }

    @GetMapping
    @Override
    public List<AuthorDto> getAllAuthor() {
        return authorService.getAllAuthors();
    }

    @Override
    @GetMapping("/{id}")
    public AuthorDto getAuthorById(@PathVariable(name = "id") Long id) {
        return authorService.findAuthorById(id);
    }

    @PutMapping("/{id}")
    @Override
    public AuthorDtoIU editAuthor(@PathVariable(name ="id") Long id, @RequestBody AuthorDtoIU authorDto) {
        return authorService.editAuthor(id, authorDto);
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteAuthor(@PathVariable(name = "id") Long id) {
        authorService.deleteAuthor(id);

    }
}
