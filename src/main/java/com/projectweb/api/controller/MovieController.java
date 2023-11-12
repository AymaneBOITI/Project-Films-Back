package com.projectweb.api.controller;
import com.projectweb.api.service.*;
import com.projectweb.api.dto.*;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;



@RestController
@RequestMapping("/movies")
@Tag(name = "Movies", description = "API endpoints for Movies")
public class MovieController {

    private final MovieService movieService;
    private static final String DEFAULT_LANGUAGE = "en-US";
    private static final int MAX_PAGE = 500;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @GetMapping("/details/{movieId}")
    @Operation(summary = "Get movie details",
            description = "Retrieve detailed information about a movie",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval of movie details",
                            content = @Content(schema = @Schema(implementation = MovieDetailsDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid movie ID supplied"),
                    @ApiResponse(responseCode = "404", description = "Movie not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ResponseEntity<MovieDetailsDTO> getMovieDetails(
            @Parameter(description = "ID of the movie to retrieve details for") @PathVariable Long movieId,
            @Parameter(description = "Language of the movie details") @RequestParam(required = false, defaultValue = "en-US") String language) {

        language = validateLanguage(language);
        MovieDetailsDTO movieDetails = movieService.getMovieDetails(movieId, language);
        if (movieDetails == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie details not found");
        }
        return ResponseEntity.ok(movieDetails);
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get movies by category", description = "Retrieve movies based on a specific category")
            @ApiResponses({
                    @ApiResponse(responseCode = "200", description = "Successful retrieval of movies by category",
                            content = @Content(schema = @Schema(implementation = MovieSummaryDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid category or page number supplied"),
                    @ApiResponse(responseCode = "404", description = "Category not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ResponseEntity<List<MovieSummaryDTO>> getMoviesByCategory(
            @Parameter(description = "Category of movies to retrieve") @PathVariable String category,
            @Parameter(description = "Language of the movie details") @RequestParam(required = false, defaultValue = "en-US") String language,
            @Parameter(description = "Page number for pagination") @RequestParam(required = false, defaultValue = "1") int page) {

        language = validateLanguage(language);
        page = validatePage(page);
        List<MovieSummaryDTO> movies = movieService.getMoviesByCategory(category, language, page);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/search")
    @Operation(summary = "Search movies", description = "Search for movies based on a query string")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful search for movies",
                    content = @Content(schema = @Schema(implementation = MovieSummaryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid query or page number supplied"),
            @ApiResponse(responseCode = "404", description = "No movies found matching the criteria"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    public ResponseEntity<List<MovieSummaryDTO>> getMoviesBySearch(
            @Parameter(description = "Query string to search for movies") @RequestParam String query,
            @Parameter(description = "Language of the movie details") @RequestParam(required = false, defaultValue = "en-US") String language,
            @Parameter(description = "Page number for pagination") @RequestParam(required = false, defaultValue = "1") int page) {

        language = validateLanguage(language);
        page = validatePage(page);
        List<MovieSummaryDTO> movies = movieService.getMoviesBySearch(query, language, page);
        return ResponseEntity.ok(movies);
    }

    private String validateLanguage(String language) {
        if (language == null || (!language.equals("en-US") && !language.equals("fr-FR"))) {
            return DEFAULT_LANGUAGE;
        }
        return language;
    }

    private int validatePage(int page) {
        if (page > MAX_PAGE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page number must be less than or equal to " + MAX_PAGE);
        }
        return page;
    }

}
