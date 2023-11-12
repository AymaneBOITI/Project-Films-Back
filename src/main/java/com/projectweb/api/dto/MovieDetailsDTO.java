package com.projectweb.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Schema(description = "Data Transfer Object for detailed movie information")
public class MovieDetailsDTO {

    @Schema(description = "Unique identifier of the movie")
    private Long id;

    @JsonProperty("original_title")
    @Schema(description = "Unique identifier of the movie")
    private String originalTitle;

    @JsonProperty("backdrop_path")
    @Schema(description = "Path to the backdrop image of the movie")
    private String backdropPath;

    @Schema(description = "List of genre names")
    private List<Genre> genres;

    @JsonProperty("original_language")
    @Schema(description = "Original language of the movie")
    private String originalLanguage;

    @Schema(description = "Overview of the movie plot")
    private String overview;

    @JsonProperty("poster_path")
    @Schema(description = "Path to the movie poster")
    private String posterPath;

    @JsonProperty("release_date")
    @Schema(description = "Release date of the movie")
    private LocalDate releaseDate;

    @Schema(description = "Runtime of the movie in minutes")
    private Integer runtime;

    @JsonProperty("vote_average")
    @Schema(description = "Average vote rating of the movie")
    private Double voteAverage;

    @Schema(description = "List of cast members")
    private List<CastDTO> cast;

    @Schema(description = "List of crew members")
    private List<CrewDTO> crew;

    @Schema(description = "Key of the first trailer video")
    private String trailerKey;

    @Schema(description = "List of paths to images related to the movie")
    private List<String> imagePaths;

    @Getter
    @Setter
    public static class Genre {
        private Long id;
        private String name;
    }
}