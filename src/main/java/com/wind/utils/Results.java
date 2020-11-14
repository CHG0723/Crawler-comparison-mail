package com.wind.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Results {
    private static final ResponseEntity NO_CONTENT;
    private static final ResponseEntity ERROR;
    private static final ResponseEntity INVALID;

    private Results() {
    }

    public static <T> ResponseEntity<T> success(T data) {

        return data == null ? NO_CONTENT : ResponseEntity.ok(data);
    }

    public static <T> ResponseEntity<T> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    public static <T> ResponseEntity<T> success() {
        return NO_CONTENT;
    }

    public static <T> ResponseEntity<T> invalid() {
        return INVALID;
    }

    public static <T> ResponseEntity<T> invalid(T data) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
    }

    public static <T> ResponseEntity<T> error() {
        return ERROR;
    }

    public static <T> ResponseEntity<T> error(T data) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);
    }

    public static <T> ResponseEntity<T> newResult(int code) {
        return ResponseEntity.status(HttpStatus.valueOf(code)).build();
    }

    public static <T> ResponseEntity<T> newResult(int code, T data) {
        return ResponseEntity.status(HttpStatus.valueOf(code)).body(data);
    }

    static {
        NO_CONTENT = new ResponseEntity(HttpStatus.NO_CONTENT);
        ERROR = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        INVALID = new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}

