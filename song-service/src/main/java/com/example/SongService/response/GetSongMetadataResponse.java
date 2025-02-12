package com.example.SongService.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetSongMetadataResponse {

    Long id;
    String name;
    String artist;
    String album;
    String duration;
    String year;

}
