package com.example.SongService.exceptionhandler;

public class SongMetadataNotFoundException extends RuntimeException {

    public SongMetadataNotFoundException(String message) {
        super(message);
    }
}

