package com.projectweb.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "Data Transfer Object for movie summary")
public class MovieSummaryDTO {

    @Schema(description = "Unique identifier of the movie")
    private Long id;

    @Schema(description = "Original title of the movie")
    private String originalTitle;

    @Schema(description = "Path to the movie poster")
    private String posterPath;

    @Schema(description = "Release date of the movie")
    private LocalDate releaseDate;

    @Schema(description = "Average vote rating of the movie")
    private Double voteAverage;
}
