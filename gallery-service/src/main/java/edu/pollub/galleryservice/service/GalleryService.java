package edu.pollub.galleryservice.service;

import edu.pollub.galleryservice.model.Image;
import edu.pollub.galleryservice.repository.GalleryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GalleryService {
    private GalleryRepository galleryRepository;

    public List<Image> getAllImages() {
        return galleryRepository.findAll();
    }
}
