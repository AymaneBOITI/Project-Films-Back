package com.projectweb.api.controller;
import com.projectweb.api.service.*;
import com.projectweb.api.dto.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/movies")
@Tag(name = "Movies", description = "API endpoints for Movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/details/{movieId}")
    @Operation(summary = "Get movie details", description = "Retrieve detailed information about a movie")
    public ResponseEntity<MovieDetailsDTO> getMovieDetails(
            @Parameter(description = "ID of the movie to retrieve details for") @PathVariable Long movieId,
            @Parameter(description = "Language of the movie details") @RequestParam(required = false, defaultValue = "en-US") String language) {

        MovieDetailsDTO movieDetails = movieService.getMovieDetails(movieId, language);
        return ResponseEntity.ok(movieDetails);
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get movies by category", description = "Retrieve movies based on a specific category")
    public ResponseEntity<List<MovieSummaryDTO>> getMoviesByCategory(
            @Parameter(description = "Category of movies to retrieve") @PathVariable String category,
            @Parameter(description = "Language of the movie details") @RequestParam(required = false, defaultValue = "en-US") String language,
            @Parameter(description = "Page number for pagination") @RequestParam(required = false, defaultValue = "1") int page) {

        List<MovieSummaryDTO> movies = movieService.getMoviesByCategory(category, language, page);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/search")
    @Operation(summary = "Search movies", description = "Search for movies based on a query string")
    public ResponseEntity<List<MovieSummaryDTO>> getMoviesBySearch(
            @Parameter(description = "Query string to search for movies") @RequestParam String query,
            @Parameter(description = "Language of the movie details") @RequestParam(required = false, defaultValue = "en-US") String language,
            @Parameter(description = "Page number for pagination") @RequestParam(required = false, defaultValue = "1") int page) {

        List<MovieSummaryDTO> movies = movieService.getMoviesBySearch(query, language, page);
        return ResponseEntity.ok(movies);
    }

}
