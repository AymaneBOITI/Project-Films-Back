package com.api.projectweb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "Data Transfer Object for detailed movie information")
public class MovieDetailsDTO extends MovieDTO {
    @Schema(description = "The runtime of the movie in minutes")
    private Integer runtime;

    @Schema(description = "The original language of the movie")
    private String originalLanguage;

    @Schema(description = "Genres of the movie")
    private List<GenreDTO> genres;

    @Schema(description = "Cast of the movie")
    private List<ActorsDTO> cast;

    @Schema(description = "Crew of the movie")
    private List<CrewDTO> crew;

    @Schema(description = "Summary of the movie")
    private String summary;

    @Schema(description = "Trailer link for the movie")
    private String trailer;

}