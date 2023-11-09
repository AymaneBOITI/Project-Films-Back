package com.api.projectweb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrewDTO {
    @Schema(description = "Crew member ID")
    private Long id;

    @Schema(description = "Crew member's name")
    private String name;

    @Schema(description = "Job title of the crew member")
    private String job;

    @Schema(description = "Path to the crew member's profile image")
    private String profilePath;
}