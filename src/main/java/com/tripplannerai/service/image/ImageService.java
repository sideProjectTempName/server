package com.tripplannerai.service.image;

import com.tripplannerai.entity.image.Image;
import com.tripplannerai.exception.image.NotImageFoundException;
import com.tripplannerai.repository.image.ImageRepository;
import com.tripplannerai.s3.S3DownloadService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final S3DownloadService s3DownloadService;

    public Resource fetchImage(Long id) throws IOException {
        Image image = imageRepository.findById(id).orElseThrow(()-> new NotImageFoundException("not image found"));
        return s3DownloadService.getFileByteArrayFromS3(image.getUrl());
    }
}
