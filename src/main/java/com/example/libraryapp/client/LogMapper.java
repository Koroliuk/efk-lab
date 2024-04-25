package com.example.libraryapp.client;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class LogMapper {

    public static Map<String, Object> createIdMap(Long id, String message) {
        return Map.of(
                "id", String.valueOf(id),
                "message", message
        );
    }

    public static Map<String, Object> createPage(String message, Integer page, Integer size) {
        return Map.of(
                "page", String.valueOf(page),
                "size", String.valueOf(size),
                "message", message
        );
    }

}
