package com.api.projectweb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Data Transfer Object for actor information")
public class ActorsDTO {
    @Schema(description = "Actor ID")
    private Long id;

    @Schema(description = "Actor's name")
    private String name;

    @Schema(description = "Path to the actor's profile image")
    private String profilePath;

    @Schema(description = "Character played by the actor")
    private String character;
}