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

/**
 * The ImageUploadAndSaveLinkService class provides methods for uploading images to Cloudinary and saving the link to the database.
 */
@Service
public class ImageUploadAndSaveLinkService {
    private final String RESPONSE_URL_KEY = "secure_url";
    private final Cloudinary cloudinary;
    private final CrudRepository<EventPost, Long> crudRepository;
    private final Logger LOG = LoggerFactory.getLogger(ImageUploadAndSaveLinkService.class);

    /**
     * Constructor for the ImageUploadAndSaveLinkService class.
     *
     * @param cloudinary     The Cloudinary object used for uploading images.
     * @param crudRepository The CrudRepository used for storing event posts.
     */
    public ImageUploadAndSaveLinkService(Cloudinary cloudinary, CrudRepository<EventPost, Long> crudRepository) {
        this.cloudinary = cloudinary;
        this.crudRepository = crudRepository;
    }

    /**
     * Upload an image to Cloudinary and return the response.
     *
     * @param file The image file to be uploaded.
     * @return The response from Cloudinary.
     * @throws IOException If an error occurs while uploading the image.
     */
    public String uploadImageAndGetLink(MultipartFile file) throws IOException {
        var response = uploadImage(file);
        return (String) response.get(RESPONSE_URL_KEY);
    }

    /**
     * Upload an image to Cloudinary and save the link to the database.
     *
     * @param id   The id of the event post to which the image belongs.
     * @param file The image file to be uploaded.
     * @return ResponseEntity indicating the result of the operation.
     * @throws IOException If an error occurs while uploading the image.
     */
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

    /**
     * Upload an image to Cloudinary and save the link to the database.
     *
     * @param id        The id of the event post to which the image belongs.
     * @param file      The image file to be uploaded.
     * @param principal The Principal object containing the username of the user making the request.
     * @return ResponseEntity indicating the result of the operation.
     * @throws IOException If an error occurs while uploading the image.
     */
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

    /**
     * Update the event post with the image link.
     *
     * @param uploadResult The response from Cloudinary.
     * @param event        The event post to be updated.
     */
    private void updateEvent(Map<?, ?> uploadResult, EventPost event) {
        var url = uploadResult.get(RESPONSE_URL_KEY);
        LOG.info("Image url: {}", url);

        event.setImageLink((String) url);

        crudRepository.save(event);
    }

    /**
     * Upload an image to Cloudinary.
     *
     * @param file The image file to be uploaded.
     * @return The response from Cloudinary.
     * @throws IOException If an error occurs while uploading the image.
     */
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
