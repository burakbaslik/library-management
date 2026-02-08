package com.burakbaslik.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "favorite_list")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable=false, unique=true)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "favorite_list_books",

            joinColumns = @JoinColumn(name = "favorite_list_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books = new HashSet<>();;


}
