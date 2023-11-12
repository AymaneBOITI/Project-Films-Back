package com.projectweb.api.apiResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MovieSummaryResponse {

    @JsonProperty("id")
    @Schema(description = "Unique identifier of the movie")
    private Long id;

    @JsonProperty("original_title")
    @Schema(description = "Original title of the movie")
    private String originalTitle;

    @JsonProperty("poster_path")
    @Schema(description = "Path to the movie poster")
    private String posterPath;

    @JsonProperty("release_date")
    @Schema(description = "Release date of the movie")
    private String releaseDate;

    @JsonProperty("vote_average")
    @Schema(description = "Average vote rating of the movie")
    private Double voteAverage;
}


