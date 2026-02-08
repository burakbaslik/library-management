package com.burakbaslik.controller.impl;

import com.burakbaslik.controller.IReadListController;
import com.burakbaslik.dto.readlist.AddBookAtReadListRequestDto;
import com.burakbaslik.dto.readlist.ReadListDtoIU;
import com.burakbaslik.service.IReadListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/read-list")
public class ReadListController implements IReadListController {
    @Autowired
    private IReadListService readListService;

    @PostMapping
    @Override
    public ReadListDtoIU addBook(@RequestBody AddBookAtReadListRequestDto requestDto) {
        return readListService.addBook(requestDto.getUserId(), requestDto.getBookId());
    }

    @GetMapping("/{id}")
    @Override
    public ReadListDtoIU getAllBooks(@PathVariable(name = "id") Long user_id) {
        return readListService.getAllBooks(user_id);
    }

    @DeleteMapping("/{user-id}/{book-id}")
    @Override
    public void deleteBook(@PathVariable("user-id") Long user_id, @PathVariable(name = "book-id") Long book_id) {
        readListService.deleteBook(user_id, book_id);

    }
}
