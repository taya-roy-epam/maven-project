package com.example.SongService.validator;

public class SongMetadataValidator {

    public static void validateId(String id) {
        if (!id.matches("^([1-9][0-9]*,)*[1-9][0-9]*$")) {
            throw new IllegalArgumentException("The provided ID is invalid (e.g., contains letters, decimals, is negative, or zero).");
        }
    }

    public static void validateIds(String ids) {
        if (ids == null || ids.isEmpty() || ids.length() > 200 || !ids.matches("^([1-9][0-9]*,)*[1-9][0-9]*$")) {
            throw new IllegalArgumentException("CSV string format is invalid or exceeds length restrictions.");
        }
    }

}

