package edu.pollub.galleryservice.service;

import com.google.auth.Credentials;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.UserCredentials;
import com.google.cloud.http.HttpTransportOptions;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import edu.pollub.galleryservice.config.ConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;

@Service
@RequiredArgsConstructor
public class GalleryService {

    private final ConfigProperties configProperties;

    public void uploadObject(String projectId, String bucketName, String objectName, byte[] object) throws IOException {
        Credentials credentials = UserCredentials.newBuilder()
                .setClientId(configProperties.getClientId())
                .setClientSecret(configProperties.getClientSecret())
                .setRefreshToken(configProperties.getRefreshToken())
                .setAccessToken((AccessToken)null)
                .setHttpTransportFactory(new HttpTransportOptions.DefaultHttpTransportFactory())
                .setTokenServerUri((URI)null)
                .setQuotaProjectId(configProperties.getQuotaProjectId())
                .build();

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

    public byte[] getObject(String projectId, String bucketName, String objectName) throws IOException {
        Credentials credentials = GoogleCredentials
                .fromStream(new FileInputStream("src/main/resources/creds.json"));

        Storage storage = StorageOptions.newBuilder().setCredentials(credentials)
                .setProjectId(projectId)
                .build().getService();

        BlobId blobId = BlobId.of(bucketName, objectName);

        return storage.get(blobId).getContent();
    }
}
