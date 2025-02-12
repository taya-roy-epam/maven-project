package com.example.SongService.controller;

import com.example.SongService.request.CreateSongMetadataRequest;
import com.example.SongService.response.CreateSongMetadataResponse;
import com.example.SongService.response.DeleteSongMetadataResponse;
import com.example.SongService.response.GetSongMetadataResponse;
import com.example.SongService.service.SongMetadataService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/songs")
public class SongMetadataController {

    @Autowired
    private SongMetadataService songMetadataService;

    @PostMapping
    public ResponseEntity<CreateSongMetadataResponse> createSongMetadata(@Valid @RequestBody CreateSongMetadataRequest createSongMetadataRequest) {
        CreateSongMetadataResponse createSongMetadataResponse = songMetadataService.createSongMetadata(createSongMetadataRequest);
        return new ResponseEntity<>(createSongMetadataResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSongMetadataResponse> getSongMetadata(@PathVariable String id) {
        GetSongMetadataResponse getSongMetadataResponse = songMetadataService.getSongMetadata(id);
        return new ResponseEntity<>(getSongMetadataResponse, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<DeleteSongMetadataResponse> deleteSongMetadata(@RequestParam("id") String ids) {
        DeleteSongMetadataResponse deleteSongMetadataResponse = songMetadataService.deleteSongMetadata(ids);
        return new ResponseEntity<>(deleteSongMetadataResponse, HttpStatus.OK);
    }

}
