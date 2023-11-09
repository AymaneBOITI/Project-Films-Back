package com.api.projectweb.controller;

import com.api.projectweb.dto.*;
import com.api.projectweb.service.*;

import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of movies",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieListDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "The resource you requested could not be found",
                            content = @Content(mediaType = "application/json"))
            })
    @GetMapping("/{type}")
    public ResponseEntity<?> getMovies(
            @PathVariable("type") String type,
            @Parameter(description = "Language of the movie list (must be 'fr-FR' or 'en-US')",
                    required = true,
                    schema = @Schema(implementation = String.class, allowableValues = {"en-US", "fr-FR"}))
            @RequestParam String language,
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

    @Operation(summary = "Get details for a specific movie",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the movie details",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieDetailsDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "The resource you requested could not be found",
                            content = @Content(mediaType = "application/json"))
            })
    @GetMapping("/details/{movieId}")
    public ResponseEntity<?> getMovieDetails(
            @PathVariable Long movieId,
            @Parameter(description = "Language for the movie details (must be 'fr-FR' or 'en-US')",
                    required = true,
                    schema = @Schema(implementation = String.class, allowableValues = {"en-US", "fr-FR"}))
            @RequestParam String language) {
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
