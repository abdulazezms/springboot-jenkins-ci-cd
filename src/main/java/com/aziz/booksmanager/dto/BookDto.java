package com.aziz.booksmanager.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ISBN;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    @NotBlank(message = "title cannot be emtpy")
    @Size(min = 1, max = 20, message = "book's title must conform to 1 <= length <= 20")
    private String title;

    @NotBlank(message = "author cannot be empty")
    @Size(min = 1, max = 20, message = "book's author must conform to 1 <= length <= 20")
    private String author;

    @Size(max = 100, message = "book's description must be <= 100 characters")

    private String description;
    @NotNull(message = "year cannot be empty")
    @Min(value = 1800, message = "year must be >= 1800")
    @Max(value = 2023, message = "year must be <= 2023")
    private Integer year;

    @NotBlank(message = "isbn cannot be empty")
    private String isbn;
}
