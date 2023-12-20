package com.bobocode.hoverla.web;

import com.bobocode.hoverla.client.ClientNasa;
import com.bobocode.hoverla.dto.NasaResponse;
import com.bobocode.hoverla.service.PictureStealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PictureStealerController {
    private final PictureStealService pictureStealService;
    private final ClientNasa clientNasa;

    @PostMapping("/pictures/steal")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void stealPictures(@RequestBody PictureStealRequest pictureStealRequest) {
        NasaResponse photos = clientNasa.getPhotos(pictureStealRequest);
        pictureStealService.stealPictures(photos);
    }

}
