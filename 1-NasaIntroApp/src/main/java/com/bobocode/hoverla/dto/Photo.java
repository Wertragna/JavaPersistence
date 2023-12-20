package com.bobocode.hoverla.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Photo(
        @JsonProperty("id") Long id,
        @JsonProperty("img_src") String imageSrc,
        @JsonProperty("camera") NasaCamera camera
) {
}
