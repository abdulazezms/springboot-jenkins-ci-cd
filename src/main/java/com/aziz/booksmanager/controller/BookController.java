package com.aziz.booksmanager.controller;

import com.aziz.booksmanager.dto.BookDto;
import com.aziz.booksmanager.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    @PostMapping
    private ResponseEntity<?> createBook(@Valid @RequestBody BookDto bookDto){
        bookService.create(bookDto);
        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @GetMapping
    private ResponseEntity<?> getBooks(){
        return ResponseEntity.status(HttpStatus.OK.value()).body(bookService.getAll());
    }
    @GetMapping("/{id}")
    private ResponseEntity<?> getBook(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK.value()).body(bookService.get(id));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteBook(@PathVariable Long id){
        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateBook(@PathVariable Long id, @Valid @RequestBody BookDto bookDto){
        bookService.update(id, bookDto);
        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }

}
