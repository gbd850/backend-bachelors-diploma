package edu.pollub.galleryservice.controller;

import edu.pollub.galleryservice.service.GalleryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/gallery")
@RequiredArgsConstructor
@Slf4j
public class GalleryController {

    private final GalleryService galleryService;

    @GetMapping(value = "/upload", headers = ("content-type=multipart/*"), consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile inputFile) throws IOException {
        galleryService.uploadObject("inzynierka", "gallery-service-storage", inputFile.getOriginalFilename(), inputFile.getBytes());

        return ResponseEntity.ok("Saved: "+inputFile.getOriginalFilename());
    }

    @GetMapping(value = "/get/{name}")
    public @ResponseBody byte[] get(@PathVariable String name) throws IOException {
        return galleryService.getObject("inzynierka", "gallery-service-storage", name);
    }


}
