package com.bobocode.hoverla.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NasaCamera(@JsonProperty("id") Long id,
                         @JsonProperty("name") String name) {

}
