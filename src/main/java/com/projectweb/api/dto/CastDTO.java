package com.projectweb.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Data Transfer Object for a cast member")
public class CastDTO {
    @Schema(description = "Name of the cast member")
    private String name;

    @JsonProperty("profile_path")
    @Schema(description = "Path to the profile image of the cast member")
    private String profilePath;

    @Schema(description = "Character played by the cast member")
    private String character;
}