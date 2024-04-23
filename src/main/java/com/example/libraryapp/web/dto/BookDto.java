package com.example.libraryapp.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Year;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDto {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    private String isbn;

    @NotNull
    private Year year;

    @NotNull
    private Long publisherId;

}
