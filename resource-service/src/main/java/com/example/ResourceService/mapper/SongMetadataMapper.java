package com.example.ResourceService.mapper;

import com.example.ResourceService.request.CreateSongMetadataRequest;
import org.apache.tika.metadata.Metadata;

public class SongMetadataMapper {

    public static CreateSongMetadataRequest mapToSongMetadata(Integer id, Metadata metadata) {
        CreateSongMetadataRequest createSongMetadataRequest = new CreateSongMetadataRequest();
        createSongMetadataRequest.setId(Long.valueOf(id));
        createSongMetadataRequest.setName(metadata.get("xmpDM:album"));
        createSongMetadataRequest.setArtist(metadata.get("xmpDM:artist"));
        createSongMetadataRequest.setAlbum(metadata.get("xmpDM:album"));
        createSongMetadataRequest.setDuration(formatDuration(metadata.get("xmpDM:duration")));
        createSongMetadataRequest.setYear(metadata.get("xmpDM:releaseDate"));
        return createSongMetadataRequest;
    }


    private static String formatDuration(String duration) {
        double durationInSeconds = Double.parseDouble(duration);

        int minutes = (int) durationInSeconds / 60;
        int seconds = (int) durationInSeconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

}
