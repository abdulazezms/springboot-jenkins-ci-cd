package com.aziz.booksmanager.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Column(name = "books_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "books_title")
    private String title;

    @Column(name = "books_author")
    private String author;

    @Column(name = "books_description")
    private String description;

    @Column(name = "books_year")
    private Integer year;

    @Column(name = "books_isbn")
    private String isbn;


}
