package com.projectweb.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDate;

@Data
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
