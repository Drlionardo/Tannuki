package com.drlionardo.registryhub.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.drlionardo.registryhub.domain.Image;
import com.drlionardo.registryhub.repo.ImageRepo;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageStorageService {
    @Value("${do.space.bucket}")
    private String doSpaceBucket;
    @Value("${do.space.endpoint}")
    private String doSpaceEndpoint;
    private final String FOLDER = "images";
    private final ImageRepo imageRepo;
    private final AmazonS3 s3Client;


    public ImageStorageService(ImageRepo imageRepo, AmazonS3 amazonS3) {
        this.imageRepo = imageRepo;
        this.s3Client= amazonS3;
    }

    public void saveFile(MultipartFile multipartFile) throws IOException {
        String filename = UUID.randomUUID().toString();
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String key = FOLDER + '/' + filename + '.' + extension;
        saveImageToServer(multipartFile, key);

        Image image = new Image();
        image.setName(filename);
        image.setExtension(extension);
        image.setCreationDate(LocalDateTime.now());
        String url = String.format("https://%s.%s/%s", doSpaceBucket, doSpaceEndpoint, key);
        image.setUrl(url);
        imageRepo.save(image);
    }

    public void deleteFile(UUID fileId) {
        Optional<Image> imageOpt = imageRepo.findById(fileId);
        if (imageOpt.isPresent()) {
            Image image = imageOpt.get();
            String key = FOLDER + '/' + image.getName() + '.' + image.getExtension();
            s3Client.deleteObject(new DeleteObjectRequest(doSpaceBucket, key));
            imageRepo.delete(image);
        }
        //TODO: Throw 404
    }

    private void saveImageToServer(MultipartFile multipartFile, String key) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getInputStream().available());
        if (multipartFile.getContentType() != null && !(multipartFile.getContentType().isEmpty())) {
            metadata.setContentType(multipartFile.getContentType());
        }
        s3Client.putObject(new PutObjectRequest(doSpaceBucket, key, multipartFile.getInputStream(), metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }
}
