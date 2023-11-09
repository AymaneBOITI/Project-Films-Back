package com.api.projectweb.controller;

import com.api.projectweb.dto.*;
import com.api.projectweb.service.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Operation(summary = "Get a list of movies by type",
            description = "Retrieve movies based on the type such as now playing, popular, top rated or upcoming",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of movies", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content),
                    @ApiResponse(responseCode = "404", description = "The type of movies was not found", content = @Content)
            })
    @GetMapping("/{type}")
    public ResponseEntity<?> getMovies(
            @PathVariable("type") String type,
            @Parameter(description = "Language of the movie list, allowed values are 'fr-FR' or 'en-US'",
                    required = true,
                    schema = @Schema(implementation = String.class, allowableValues = {"en-US", "fr-FR"}))
            @RequestParam(value = "language") String language,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        try {
            MovieListDTO movieList = movieService.getMovies(type, language, page);
            return ResponseEntity.ok(movieList);
        } catch (MovieServiceException e) {
            return ResponseEntity
                    .status(e.getStatus() != null ? e.getStatus() : HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @Operation(summary = "Get movie details by ID",
            description = "Retrieve detailed information about a movie by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the movie details", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Invalid movie ID", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Movie not found", content = @Content)
            })
    @GetMapping("/details/{movieId}")
    public ResponseEntity<?> getMovieDetails(
            @PathVariable("movieId") Long movieId,
            @Parameter(description = "Language for the movie details, allowed values are 'fr-FR' or 'en-US'",
                    required = true,
                    schema = @Schema(implementation = String.class, allowableValues = {"en-US", "fr-FR"}))
            @RequestParam(value = "language") String language) {
        try {
            MovieDetailsDTO movieDetails = movieService.getMovieDetails(movieId, language);
            return ResponseEntity.ok(movieDetails);
        } catch (MovieServiceException e) {
            return ResponseEntity
                    .status(e.getStatus() != null ? e.getStatus() : HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

}


