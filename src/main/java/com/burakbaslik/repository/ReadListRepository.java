package com.burakbaslik.repository;

import com.burakbaslik.dto.readlist.ReadListDtoIU;
import com.burakbaslik.model.ReadList;
import com.burakbaslik.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReadListRepository extends JpaRepository<ReadList,Long> {
    Optional<ReadList> findByUser(User user);
    List<ReadList> findAllByBooks_Id(Long bookId);

    @Modifying
    @Query(value = "DELETE FROM read_list_books WHERE book_id IN (:bookIds)", nativeQuery = true)
    void deleteJoinsByBookIds(@Param("bookIds") List<Long> bookIds);
}
