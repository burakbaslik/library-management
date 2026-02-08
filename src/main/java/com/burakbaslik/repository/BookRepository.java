package com.burakbaslik.repository;

import com.burakbaslik.model.Book;
import com.burakbaslik.model.FavoriteList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findAllByAuthor_Id(Long authorId);

    @Query("select b.id from Book b where b.author.id = :authorId")
    List<Long> findIdsByAuthorId(@Param("authorId") Long authorId);

    @Modifying
    @Query("delete from Book b where b.author.id = :authorId")
    void deleteByAuthorId(@Param("authorId") Long authorId);
}
