package com.example.SongService.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSongMetadataRequest {

    @NotNull(message = "ID cannot be null")
    @Min(value = 1, message = "ID must be a positive integer")
    private Long id;

    @NotBlank(message = "Song name cannot be empty")
    @Size(min = 1, max = 100, message = "Song name must be between 1 and 100 characters")
    private String name;

    @NotBlank(message = "Artist name cannot be empty")
    @Size(min = 1, max = 100, message = "Artist name must be between 1 and 100 characters")
    private String artist;

    @NotBlank(message = "Album name cannot be empty")
    @Size(min = 1, max = 100, message = "Album name must be between 1 and 100 characters")
    private String album;

    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9]):[0-5][0-9]$",
            message = "Duration must be in format mm:ss with leading zeros")
    private String duration;

    @Pattern(regexp = "^(19[0-9]{2}|20[0-9]{2})$",
            message = "Year must be in YYYY format between 1900 and 2099")
    private String year;
}

