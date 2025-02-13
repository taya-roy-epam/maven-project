package com.example.ResourceService.validator;

import org.apache.tika.metadata.Metadata;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ResourceValidator {

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    public static void validateFile(byte[] fileBytes, String contentType) {

        if (!"audio/mpeg".equals(contentType) || fileBytes == null || fileBytes.length == 0 || fileBytes.length > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("The request body is invalid MP3.");
        }
    }

    public static void validateMetadata(Metadata metadata) {
        if (metadata == null) {
            throw new IllegalArgumentException("Song metadata is missing or contains errors.");
        }

        List<String> metadataKeys = Arrays.asList("xmpDM:album", "xmpDM:artist", "xmpDM:duration", "xmpDM:releaseDate");

        boolean hasMissingValues = metadataKeys.stream()
                .anyMatch(key -> metadata.get(key).isEmpty());

        if (hasMissingValues) {
            throw new IllegalArgumentException("Song metadata is missing or contains errors.");
        }
    }

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

