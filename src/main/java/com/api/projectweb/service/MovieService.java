package com.api.projectweb.service;
import com.api.projectweb.dto.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(tmdbApiUrl + type)
                .queryParam("language", language)
                .queryParam("page", page);

        try {
            ResponseEntity<MovieListDTO> response = restTemplate.getForEntity(uriBuilder.toUriString(), MovieListDTO.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new MovieServiceException("Failed to fetch movies: Status code " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new MovieServiceException("Error while calling TMDb API", e);
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
                throw new MovieServiceException("Failed to fetch movie details: Status code " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new MovieServiceException("Error while calling TMDb API", e);
        }
    }
}

