package edu.pollub.galleryservice.controller;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import edu.pollub.galleryservice.model.Image;
import edu.pollub.galleryservice.service.GalleryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.FileInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/gallery")
@RequiredArgsConstructor
@Slf4j
public class GalleryController {

    private final GalleryService galleryService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Image> getAllImages() {
        return galleryService.getAllImages();
    }

    @GetMapping(value = "/upload", headers = ("content-type=multipart/*"), consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity upload(@RequestParam("file") MultipartFile inputFile) throws IOException {
        uploadObject("inzynierka", "gallery-service-storage", inputFile.getOriginalFilename(), inputFile.getBytes());

        galleryService.saveImage("https://storage.cloud.google.com/gallery-service-storage/"+inputFile.getOriginalFilename());

        return ResponseEntity.ok("Saved: "+inputFile.getOriginalFilename());
    }

    public void uploadObject(String projectId, String bucketName, String objectName, byte[] object) throws IOException {
        Credentials credentials = GoogleCredentials
                .fromStream(new FileInputStream("src/main/resources/creds.json"));

        Storage storage = StorageOptions.newBuilder().setCredentials(credentials)
                .setProjectId(projectId)
                .build().getService();

        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType("image/jpeg")
                .build();

        Storage.BlobWriteOption precondition;
        if (storage.get(bucketName, objectName) == null) {
            precondition = Storage.BlobWriteOption.doesNotExist();
        } else {
            precondition =
                    Storage.BlobWriteOption.generationMatch(
                            storage.get(bucketName, objectName).getGeneration());
        }
        storage.createFrom(blobInfo, new ByteArrayInputStream(object), precondition);
    }
}
