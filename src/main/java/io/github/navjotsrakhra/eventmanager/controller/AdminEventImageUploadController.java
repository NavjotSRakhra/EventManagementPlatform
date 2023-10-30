/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.controller;

import io.github.navjotsrakhra.eventmanager.service.ImageUploadAndSaveLinkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("admin/events/image")
public class AdminEventImageUploadController {
    private final ImageUploadAndSaveLinkService imageUploadAndSaveLinkService;

    public AdminEventImageUploadController(ImageUploadAndSaveLinkService imageUploadAndSaveLinkService) {
        this.imageUploadAndSaveLinkService = imageUploadAndSaveLinkService;
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<Void> uploadImage(MultipartFile file, @PathVariable Long id) throws IOException {
        return imageUploadAndSaveLinkService.uploadImageAndSaveLink(id, file);
    }
}
