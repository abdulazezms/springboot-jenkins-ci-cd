package com.aziz.booksmanager.service;

import com.aziz.booksmanager.dto.BookDto;

import java.util.List;

public interface BookService {
    void create(BookDto bookDto);
    BookDto get(Long id);

    List<BookDto> getAll();

    void delete(Long id);

    void update(Long id, BookDto bookDto);
}
