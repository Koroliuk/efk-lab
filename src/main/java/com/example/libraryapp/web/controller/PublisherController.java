package com.example.libraryapp.web.controller;

import com.example.libraryapp.persistence.model.Publisher;
import com.example.libraryapp.service.PublisherService;
import com.example.libraryapp.web.dto.DtoMapper;
import com.example.libraryapp.web.dto.PublisherDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("publishers")
public class PublisherController {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public ResponseEntity<List<PublisherDto>> getAllPublishers(@RequestParam(required = false) Integer page,
                                                               @RequestParam(required = false) Integer size) {
        List<PublisherDto> publishers;
        if (page != null && size != null) {
            if (page <= 0 || size <= 0) {
                return ResponseEntity.badRequest().build();
            }
            publishers = publisherService.findAll(page, size).stream()
                    .map(DtoMapper::convertToDto)
                    .toList();
        } else {
            publishers = publisherService.findAll().stream()
                    .map(DtoMapper::convertToDto)
                    .toList();
        }
        return ResponseEntity.ok(publishers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDto> getPublisher(@PathVariable Long id) {
        return publisherService.findById(id)
                .map(publisher -> ResponseEntity.ok(DtoMapper.convertToDto(publisher)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PublisherDto> createPublisher(@Valid @RequestBody PublisherDto publisherDto) {
        Publisher publisher = publisherService.create(publisherDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoMapper.convertToDto(publisher));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherDto> updatePublisher(@PathVariable Long id, @Valid @RequestBody PublisherDto publisherDto) {
        publisherService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        publisherDto.setId(id);
        Publisher updatedPublisher = publisherService.update(publisherDto);
        return ResponseEntity.ok(DtoMapper.convertToDto(updatedPublisher));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        publisherService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        publisherService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
