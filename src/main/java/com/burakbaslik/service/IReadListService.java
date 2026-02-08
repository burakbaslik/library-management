package com.burakbaslik.service;

import com.burakbaslik.dto.book.BookDto;
import com.burakbaslik.dto.readlist.ReadListDtoIU;

public interface IReadListService {
    public ReadListDtoIU addBook(Long book_id, Long user_id);
    public ReadListDtoIU getAllBooks(Long user_id);
    public void deleteBook(Long book_id, Long user_id);


}
