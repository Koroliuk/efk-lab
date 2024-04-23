package com.example.libraryapp.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PublisherDto {

    private Long id;

    @NotBlank
    private String name;

}
