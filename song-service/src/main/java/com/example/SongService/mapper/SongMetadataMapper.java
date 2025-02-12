package com.example.SongService.mapper;

import com.example.SongService.entity.SongMetadata;
import com.example.SongService.request.CreateSongMetadataRequest;
import com.example.SongService.response.GetSongMetadataResponse;

public class SongMetadataMapper {

    public static SongMetadata requestToEntity(CreateSongMetadataRequest createSongMetadataRequest) {

        return new SongMetadata(
                createSongMetadataRequest.getId(),
                createSongMetadataRequest.getName(),
                createSongMetadataRequest.getArtist(),
                createSongMetadataRequest.getAlbum(),
                createSongMetadataRequest.getDuration(),
                createSongMetadataRequest.getYear()
        );
    }

    public static GetSongMetadataResponse entityToResponse(SongMetadata songMetadata) {

        return new GetSongMetadataResponse(
                songMetadata.getId(),
                songMetadata.getName(),
                songMetadata.getArtist(),
                songMetadata.getAlbum(),
                songMetadata.getDuration(),
                songMetadata.getYear()
        );
    }
}
