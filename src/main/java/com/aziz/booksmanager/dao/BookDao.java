package com.aziz.booksmanager.dao;

import com.aziz.booksmanager.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  BookDao extends JpaRepository<Book, Long> {
    Boolean existsByIsbn(String isbn);

}
