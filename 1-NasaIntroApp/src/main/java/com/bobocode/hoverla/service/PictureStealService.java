package com.bobocode.hoverla.service;

import com.bobocode.hoverla.domain.Camera;
import com.bobocode.hoverla.domain.CameraRepository;
import com.bobocode.hoverla.domain.Picture;
import com.bobocode.hoverla.domain.PictureRepository;
import com.bobocode.hoverla.dto.NasaCamera;
import com.bobocode.hoverla.dto.NasaResponse;
import com.bobocode.hoverla.dto.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PictureStealService {
    private final PictureRepository picturesRepository;
    private final CameraRepository cameraRepository;
    private final ObjectProvider<PictureStealService> objectProvider;

    @Transactional(propagation = Propagation.NEVER)
    public void stealPictures(NasaResponse nasaResponse) {
        nasaResponse.photos().forEach(self()::savePhoto);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void savePhoto(Photo photo) {
        NasaCamera nasaCamera = photo.camera();

        Picture picture = picturesRepository.findByNasaId(photo.id())
                .orElseGet(() -> Picture.builder()
                        .nasaId(photo.id())
                        .imageSrc(photo.imageSrc())
                        .camera(cameraRepository.findByNasaId(nasaCamera.id())
                                .orElseGet(() -> Camera.builder()
                                        .nasaId(nasaCamera.id())
                                        .name(nasaCamera.name())
                                        .build()))
                        .build());

        picturesRepository.save(picture);
    }


    private PictureStealService self() {
        return Objects.requireNonNull(objectProvider.getIfAvailable(), "SELF reference to PictureStealService bean is null");
    }

}
