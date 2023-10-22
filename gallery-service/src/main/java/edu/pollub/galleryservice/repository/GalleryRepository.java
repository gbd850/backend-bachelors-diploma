package edu.pollub.galleryservice.repository;

import edu.pollub.galleryservice.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryRepository extends JpaRepository<Image, Integer> {
}
