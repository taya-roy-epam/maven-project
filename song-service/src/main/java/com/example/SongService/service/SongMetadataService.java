package com.example.SongService.service;

import com.example.SongService.entity.SongMetadata;
import com.example.SongService.exceptionhandler.SongMetadataConflictException;
import com.example.SongService.exceptionhandler.SongMetadataNotFoundException;
import com.example.SongService.mapper.SongMetadataMapper;
import com.example.SongService.repository.SongMetadataRepository;
import com.example.SongService.request.CreateSongMetadataRequest;
import com.example.SongService.response.CreateSongMetadataResponse;
import com.example.SongService.response.DeleteSongMetadataResponse;
import com.example.SongService.response.GetSongMetadataResponse;
import com.example.SongService.validator.SongMetadataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class SongMetadataService {

    @Autowired
    private SongMetadataRepository songMetadataRepository;

    public CreateSongMetadataResponse createSongMetadata(CreateSongMetadataRequest createSongMetadataRequest) {
        Optional<SongMetadata> songMetadata = songMetadataRepository.findById(createSongMetadataRequest.getId());
        if(songMetadata.isPresent()) {
            throw new SongMetadataConflictException("Metadata for this ID already exists.");
        }
        return new CreateSongMetadataResponse(
                songMetadataRepository.save(SongMetadataMapper.requestToEntity(createSongMetadataRequest)).getId());
    }

    public GetSongMetadataResponse getSongMetadata(String id) {
        SongMetadataValidator.validateId(id);
        return songMetadataRepository.findById(Long.valueOf(id))
                .map(SongMetadataMapper::entityToResponse)
                .orElseThrow(() -> new SongMetadataNotFoundException("Resource with ID=" + id + " not found"));
    }

    public DeleteSongMetadataResponse deleteSongMetadata(String ids) {
        SongMetadataValidator.validateIds(ids);
        List<String> idList = Arrays.stream(ids.split(","))
                .collect(Collectors.toList());

        List<Integer> deletedIds = new ArrayList<>();

        AtomicInteger count = new AtomicInteger(0);
        idList.stream().forEach(id -> {
            Optional<SongMetadata> songMetadata = songMetadataRepository.findById(Long.valueOf(id));
            if(songMetadata.isPresent()) {
                songMetadataRepository.deleteById(Long.valueOf(id));
                deletedIds.add(Integer.valueOf(id));
            }
        });

        return new DeleteSongMetadataResponse(deletedIds.toArray(new Integer[0]));
    }

}
