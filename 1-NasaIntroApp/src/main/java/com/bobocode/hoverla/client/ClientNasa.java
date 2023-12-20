package com.bobocode.hoverla.client;

import com.bobocode.hoverla.dto.NasaResponse;
import com.bobocode.hoverla.web.PictureStealRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ClientNasa {

    public ClientNasa(RestTemplate restTemplate,
                      @Value("${nasa-api.api-key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    private final RestTemplate restTemplate;
    private final String apiKey;

    public NasaResponse getPhotos(@RequestBody PictureStealRequest pictureStealRequest) {
        return restTemplate.getForObject(api()
                        .path("/mars-photos/api/v1/rovers/curiosity/photos")
                        .queryParam("sol", pictureStealRequest.sol())
                        .queryParam("api_key", apiKey)
                        .build()
                        .toUri(),
                NasaResponse.class);
    }

    public UriComponentsBuilder api() {
        return UriComponentsBuilder.fromHttpUrl("https://api.nasa.gov");
    }
}
