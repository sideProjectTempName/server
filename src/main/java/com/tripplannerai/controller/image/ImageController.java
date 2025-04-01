package com.tripplannerai.controller.image;

import com.tripplannerai.annotation.Username;
import com.tripplannerai.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    @GetMapping("/image/{id}")
    public Resource fetchImage(@PathVariable Long id) throws IOException {
        return imageService.fetchImage(id);
    }
}
