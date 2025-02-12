package com.example.ResourceService.controller;

import com.example.ResourceService.response.DeleteResourceResponse;
import com.example.ResourceService.response.UploadResourceResponse;
import com.example.ResourceService.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadResourceResponse> uploadResource(@RequestParam("file") MultipartFile file) {
        UploadResourceResponse uploadResourceResponse = resourceService.uploadResource(file);
        return new ResponseEntity<>(uploadResourceResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getResource(@PathVariable String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("audio/mpeg"));
        return new ResponseEntity<>(resourceService.getResource(id), headers, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<DeleteResourceResponse> deleteSongMetadata(@RequestParam("id") String ids) {
        DeleteResourceResponse deleteResourceResponse = resourceService.deleteResource(ids);
        return new ResponseEntity<>(deleteResourceResponse, HttpStatus.OK);
    }

}
