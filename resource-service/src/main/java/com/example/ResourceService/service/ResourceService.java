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
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.xml.sax.ContentHandler;

import java.io.ByteArrayInputStream;
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

    public UploadResourceResponse uploadResource(byte[] fileBytes, String contentType) {
        ResourceValidator.validateFile(fileBytes, contentType);
        Resource resource = new Resource();
        resource.setFile(fileBytes);
        Metadata metadata = extractMetadata(fileBytes);
        ResourceValidator.validateMetadata(metadata);
        resourceRepository.save(resource);
        songServiceClient.createSongMetadata(SongMetadataMapper.mapToSongMetadata(resource.getId(), metadata));
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

        if(deletedIds.size() != 0) {
            songServiceClient.deleteSongMetadata(deletedIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")));
        }

        return new DeleteResourceResponse(deletedIds.toArray(new Integer[0]));
    }

    private Metadata extractMetadata(byte[] fileBytes) {
        try (InputStream input = new ByteArrayInputStream(fileBytes)) {
            ContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            AutoDetectParser parser = new AutoDetectParser();
            parser.parse(input, handler, metadata, new ParseContext());
            return metadata;
        } catch (Exception e) {
            throw new IllegalArgumentException("The request body is invalid MP3.");
        }
    }

}
