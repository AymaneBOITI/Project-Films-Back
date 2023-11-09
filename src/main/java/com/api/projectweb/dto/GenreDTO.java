package com.api.projectweb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Data Transfer Object for genre information")
public class GenreDTO {
    @Schema(description = "Genre ID")
    private Integer id;

    @Schema(description = "Name of the genre")
    private String name;
}