package com.jumbo.store.geo.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Request DTO for finding the nearest store")
public record NearestStoreRequest(
    @NotNull(message = "Latitude is required")
    @Pattern(regexp = "^-?\\d+(\\.\\d+)?$", message = "Latitude must be a valid decimal number")
    @Schema(description = "Latitude of the location", example = "52.379189")
    String latitude,

    

    @NotNull(message = "Longitude is required")
    @Pattern(regexp = "^-?\\d+(\\.\\d+)?$", message = "Longitude must be a valid decimal number")
    @Schema(description = "Longitude of the location", example = "4.899431")
    String longitude
) {}