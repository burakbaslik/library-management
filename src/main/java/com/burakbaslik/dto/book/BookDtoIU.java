package com.burakbaslik.dto.book;

import com.burakbaslik.model.Author;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDtoIU {
    private Long id;
    private String title;
    private String description;
    private Long authorId;
}
