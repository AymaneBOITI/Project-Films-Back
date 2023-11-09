package com.api.projectweb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "Data Transfer Object for movie lists")
public class MovieListDTO {
    @Schema(description = "The page number of the movie list")
    private int page;

    @Schema(description = "List of movies")
    private List<MovieDTO> results;

    @JsonProperty("total_results")
    @Schema(description = "Total number of results available")
    private int totalResults;

    @JsonProperty("total_pages")
    @Schema(description = "Total number of pages available")
    private int totalPages;
}