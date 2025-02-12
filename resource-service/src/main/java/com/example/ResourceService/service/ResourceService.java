package com.example.ResourceService.service;

import com.example.ResourceService.entity.Resource;
import com.example.ResourceService.exceptionhandler.ResourceNotFoundException;
import com.example.ResourceService.mapper.SongMetadataMapper;
import com.example.ResourceService.repository.ResourceRepository;
import com.example.ResourceService.response.DeleteResourceResponse;
import com.example.ResourceService.response.UploadResourceResponse;
import com.example.ResourceService.rest.client.SongServiceClient;
import com.example.ResourceService.validator.ResourceValidator;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.xml.sax.ContentHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private SongServiceClient songServiceClient;

    public UploadResourceResponse uploadResource(MultipartFile file) {
        ResourceValidator.validateFile(file);
        Resource resource = new Resource();
        try {
            resource.setFile(file.getBytes());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred on the server.");
        }
        Metadata metadata = extractMetadata(file);
        ResourceValidator.validateMetadata(metadata);
        resourceRepository.save(resource);
        System.out.println(songServiceClient.createSongMetadata(SongMetadataMapper.mapToSongMetadata(resource.getId(), metadata)));
        return new UploadResourceResponse(resource.getId());
    }

    public byte[] getResource(String id) {
        ResourceValidator.validateId(id);
        Optional<Resource> resource = resourceRepository.findById(Long.valueOf(id));
        if(resource.isPresent()) {
            return resource.get().getFile();
        }
        throw new ResourceNotFoundException("Resource with ID=" + id + " not found");
    }

    public DeleteResourceResponse deleteResource(String ids) {
        ResourceValidator.validateIds(ids);
        List<String> idList = Arrays.stream(ids.split(","))
                .collect(Collectors.toList());

        List<Integer> deletedIds = new ArrayList<>();

        AtomicInteger count = new AtomicInteger(0);
        idList.stream().forEach(id -> {
            Optional<Resource> resource = resourceRepository.findById(Long.valueOf(id));
            if(resource.isPresent()) {
                resourceRepository.deleteById(Long.valueOf(id));
                deletedIds.add(Integer.valueOf(id));
            }
        });

        songServiceClient.deleteSongMetadata(deletedIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")));

        return new DeleteResourceResponse(deletedIds.toArray(new Integer[0]));
    }

    private Metadata extractMetadata(MultipartFile file) {
        Metadata metadata = new Metadata();

        try (InputStream input = file.getInputStream()) {
            ContentHandler handler = new BodyContentHandler();
            Mp3Parser mp3Parser = new Mp3Parser();
            ParseContext parseContext = new ParseContext();
            mp3Parser.parse(input, handler, metadata, parseContext);
        } catch (Exception e) {
            throw new IllegalArgumentException("The request body is invalid MP3.");
        }

        return metadata;
    }

}
