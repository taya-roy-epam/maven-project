package com.example.SongService.exceptionhandler;

public class SongMetadataConflictException extends RuntimeException {

    public SongMetadataConflictException(String message) {
        super(message);
    }
}

