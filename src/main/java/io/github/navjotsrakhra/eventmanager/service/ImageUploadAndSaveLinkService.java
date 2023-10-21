/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.navjotsrakhra.eventmanager.dataModel.EventPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@Service
public class ImageUploadAndSaveLinkService {
    private final Cloudinary cloudinary;
    private final CrudRepository<EventPost, Long> crudRepository;
    private final Logger LOG = LoggerFactory.getLogger(ImageUploadAndSaveLinkService.class);

    public ImageUploadAndSaveLinkService(Cloudinary cloudinary, CrudRepository<EventPost, Long> crudRepository) {
        this.cloudinary = cloudinary;
        this.crudRepository = crudRepository;
    }

    public ResponseEntity<Void> uploadImageAndSaveLink(long id, MultipartFile file) throws IOException {

        var eventPostOptional = crudRepository.findById(id);

        if (eventPostOptional.isEmpty()) return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();

        var event = eventPostOptional.get();

        var uploadResult = uploadImage(file);
        updateEvent(uploadResult, event);

        return ResponseEntity
                .ok()
                .build();
    }

    public ResponseEntity<Void> uploadImageAndSaveLink(long id, MultipartFile file, Principal principal) throws IOException {

        var eventPostOptional = crudRepository.findById(id);

        if (eventPostOptional.isEmpty()) return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();

        var event = eventPostOptional.get();

        if (!event.getPostedBy().equals(principal.getName()))
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();

        var uploadResult = uploadImage(file);
        updateEvent(uploadResult, event);

        return ResponseEntity
                .ok()
                .build();
    }

    private void updateEvent(Map<?, ?> uploadResult, EventPost event) {
        var url = uploadResult.get("url");
        LOG.info("Image url: {}", url);

        event.setImageLink((String) url);

        crudRepository.save(event);
    }

    private Map uploadImage(MultipartFile file) throws IOException {
        var params = ObjectUtils.asMap(
                "public_id", "eventmanagement/" + UUID.randomUUID(),
                "overwrite", true,
                "resource_type", "image"
        );

        var fileName = UUID.randomUUID().toString();
        var in = file.getInputStream();
        var f = new File(fileName);
        f.createNewFile();

        var fos = new FileOutputStream(fileName);

        fos.write(in.readAllBytes());
        fos.close();


        f.deleteOnExit();
        return cloudinary.uploader().upload(new File(fileName), params);
    }
}
