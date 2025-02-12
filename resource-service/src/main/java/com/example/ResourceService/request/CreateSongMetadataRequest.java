package com.example.ResourceService.request;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSongMetadataRequest {

    Long id;
    String name;
    String artist;
    String album;
    String duration;
    String year;

}
