package com.api.projectweb.controller;

import com.api.projectweb.dto.*;
import com.api.projectweb.service.*;

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

    @GetMapping("/{type}")
    public ResponseEntity<?> getMovies(
            @PathVariable("type") String type,
            @RequestParam(value = "language", defaultValue = "en-US") String language,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        try {
            MovieListDTO movieList = movieService.getMovies(type, language, page);
            return ResponseEntity.ok(movieList);
        } catch (MovieServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/details/{movieId}")
    public ResponseEntity<?> getMovieDetails(
            @PathVariable("movieId") Long movieId,
            @RequestParam(value = "language", defaultValue = "en-US") String language) {
        try {
            MovieDetailsDTO movieDetails = movieService.getMovieDetails(movieId, language);
            return ResponseEntity.ok(movieDetails);
        } catch (MovieServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
