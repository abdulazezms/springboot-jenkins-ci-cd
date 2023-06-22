package com.aziz.booksmanager.service;

import com.aziz.booksmanager.dao.BookDao;
import com.aziz.booksmanager.domain.Book;
import com.aziz.booksmanager.dto.BookDto;
import com.aziz.booksmanager.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
    private final BookDao bookDao;

    @Override
    public void create(BookDto bookDto) {
        if(bookDao.existsByIsbn(bookDto.getIsbn())){
            throw new BusinessException("Book already exists");
        }
        bookDao.save(bookDtoToBook(bookDto));
    }

    private Book bookDtoToBook(BookDto bookDto) {
        return Book
                .builder()
                .author(bookDto.getAuthor())
                .description(bookDto.getDescription())
                .title(bookDto.getTitle())
                .isbn(bookDto.getIsbn())
                .year(bookDto.getYear())
                .build();
    }

    private BookDto bookToBookDto(Book book) {
        return BookDto
                .builder()
                .author(book.getAuthor())
                .description(book.getDescription())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .year(book.getYear())
                .build();
    }

    @Override
    public BookDto get(Long id) {
        if(bookDao.existsById(id)){
            return bookToBookDto(bookDao.getReferenceById(id));
        }
        throw new BusinessException("No book with such ID exists");
    }



    @Override
    public List<BookDto> getAll() {
        return bookDao.findAll().stream().map(this::bookToBookDto).toList();
    }

    @Override
    public void delete(Long id) {
        if(!bookDao.existsById(id)){
            throw new BusinessException("No book with such ID exists");
        }
        bookDao.deleteById(id);
    }

    @Override
    public void update(Long id, BookDto bookDto) {
        if(!bookDao.existsById(id)){
            throw new BusinessException("No book with such ID exists");
        }
        Book persisted = bookDao.getReferenceById(id);
        if(!bookDto.getIsbn().equals(persisted.getIsbn()) && bookDao.existsByIsbn(bookDto.getIsbn())){
            throw new BusinessException("ISBN already exists!");
        }
        Book updated = bookDtoToBook(bookDto);
        updated.setId(id);
        bookDao.save(updated);
    }
}
