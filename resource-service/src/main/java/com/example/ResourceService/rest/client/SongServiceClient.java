package com.example.ResourceService.rest.client;

import com.example.ResourceService.request.CreateSongMetadataRequest;
import com.example.ResourceService.response.CreateSongMetadataResponse;
import com.example.ResourceService.response.DeleteSongMetadataResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SongServiceClient {

    private final RestClient restClient;

    public SongServiceClient() {
        restClient = RestClient.builder()
                .baseUrl("http://localhost:8081/songs")
                .build();
    }

    public CreateSongMetadataResponse createSongMetadata(CreateSongMetadataRequest createSongMetadataRequest) {
        return restClient.post()
                .uri("")
                .contentType(MediaType.APPLICATION_JSON)
                .body(createSongMetadataRequest)
                .retrieve()
                .body(CreateSongMetadataResponse.class);
    }

    public DeleteSongMetadataResponse deleteSongMetadata(String ids) {
        return restClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("id", ids)
                        .build())
                .retrieve()
                .body(DeleteSongMetadataResponse.class);
    }
}


