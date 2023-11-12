package com.projectweb.api.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Data Transfer Object for a crew member")
public class CrewDTO {
    @Schema(description = "Name of the crew member")
    private String name;

    @Schema(description = "Job or role of the crew member in the production")
    private String job;

    @JsonProperty("profile_path")
    @Schema(description = "Path to the profile image of the crew member")
    private String profilePath;
}