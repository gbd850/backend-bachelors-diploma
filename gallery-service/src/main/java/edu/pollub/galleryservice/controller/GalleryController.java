package edu.pollub.galleryservice.controller;

import edu.pollub.galleryservice.model.Image;
import edu.pollub.galleryservice.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gallery")
@RequiredArgsConstructor
public class GalleryController {

    private GalleryService galleryService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Image> getAllImages() {
        return galleryService.getAllImages();
    }
}
