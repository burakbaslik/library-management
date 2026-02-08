package com.burakbaslik.repository;

import com.burakbaslik.dto.favoritelist.FavoriteListBooksDto;
import com.burakbaslik.model.Book;
import com.burakbaslik.model.FavoriteList;
import com.burakbaslik.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteListBooksRepository extends JpaRepository<Book, FavoriteList> {

}
