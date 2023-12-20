package com.bobocode.hoverla.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record NasaResponse(@JsonProperty("photos") List<Photo> photos) {
}
