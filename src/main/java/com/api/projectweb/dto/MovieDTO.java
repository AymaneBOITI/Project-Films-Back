package com.api.projectweb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "Data Transfer Object for individual movie details")
public class MovieDTO {
    @Schema(description = "Movie ID")
    private Long id;

    @Schema(description = "Movie title")
    private String title;

    @JsonProperty("release_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "Movie release date")
    private String releaseDate;

    @JsonProperty("poster_path")
    @Schema(description = "Path to the movie poster image")
    private String posterPath;

    @JsonProperty("genre_ids")
    @Schema(description = "List of genre IDs for the movie")
    private List<Integer> genreIds;

    @Schema(description = "Popularity score of the movie")
    private Double popularity;

    @Schema(description = "Overview of the movie's plot")
    private String overview;
}