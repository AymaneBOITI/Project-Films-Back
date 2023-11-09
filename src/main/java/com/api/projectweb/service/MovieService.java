package com.api.projectweb.service;
import com.api.projectweb.dto.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpStatus;


@Service
public class MovieService {

    private final RestTemplate restTemplate;

    @Value("${tmdb.api.url}")
    private String tmdbApiUrl;

    @Autowired
    public MovieService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MovieListDTO getMovies(String type, String language, int page) throws MovieServiceException {
        if (!"fr-FR".equals(language) && !"en-US".equals(language)) {
            throw new MovieServiceException("Language must be 'fr-FR' or 'en-US'", HttpStatus.BAD_REQUEST);
        }

        String endpoint = switch (type) {
            case "now_playing" -> "movie/now_playing";
            case "popular" -> "movie/popular";
            case "top_rated" -> "movie/top_rated";
            case "upcoming" -> "movie/upcoming";
            default -> throw new MovieServiceException("Invalid movie type specified", HttpStatus.BAD_REQUEST);
        };

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(tmdbApiUrl + endpoint)
                .queryParam("language", language)
                .queryParam("page", page);

        try {
            ResponseEntity<MovieListDTO> response = restTemplate.getForEntity(uriBuilder.toUriString(), MovieListDTO.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new MovieServiceException("Failed to fetch movies: Status code " + response.getStatusCode(), (HttpStatus) response.getStatusCode());
            }
        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
            throw new MovieServiceException("Error from TMDb API: " + e.getResponseBodyAsString(), (HttpStatus) e.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MovieServiceException("Error while calling TMDb API", e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public MovieDetailsDTO getMovieDetails(Long movieId, String language) throws MovieServiceException {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(tmdbApiUrl + "movie/" + movieId)
                .queryParam("language", language)
                .queryParam("append_to_response", "credits,videos");

        try {
            ResponseEntity<MovieDetailsDTO> response = restTemplate.getForEntity(uriBuilder.toUriString(), MovieDetailsDTO.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new MovieServiceException("Failed to fetch movie details: Status code " + response.getStatusCode(), (HttpStatus) response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            throw new MovieServiceException("Not Found: " + e.getResponseBodyAsString(), (HttpStatus) e.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MovieServiceException("Error while calling TMDb API", e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


