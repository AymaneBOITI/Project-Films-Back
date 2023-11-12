package com.projectweb.api.service;
import com.projectweb.api.apiResponse.*;
import com.projectweb.api.apiResponse.MovieSummaryResponse;
import com.projectweb.api.apiResponse.VideosResponse;
import com.projectweb.api.dto.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    @Value("${tmdb.api.baseurl}")
    private String tmdbBaseUrl;

    @Value("${tmdb.bearer.token}")
    private String bearerToken;

    private final RestTemplate restTemplate;

    public MovieService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MovieDetailsDTO getMovieDetails(Long movieId, String language) {
        try {
            MovieDetailsDTO movieDetails = fetchMovieDetails(movieId, language);
            movieDetails.setCast(fetchCastDetails(movieId, language));
            movieDetails.setCrew(fetchCrewDetails(movieId, language));
            movieDetails.setTrailerKey(fetchTrailerKey(movieId, language));
            movieDetails.setImagePaths(fetchImagePaths(movieId));
            return movieDetails;
        } catch (Exception e) {
            logger.error("Error getting movie details for movie ID " + movieId, e);
            throw new RuntimeException("Error fetching movie details", e);
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken);
        return headers;
    }

    private MovieDetailsDTO fetchMovieDetails(Long movieId, String language) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(tmdbBaseUrl + "/movie/" + movieId)
                    .queryParam("language", language)
                    .toUriString();

            ResponseEntity<MovieDetailsDTO> response = restTemplate.exchange(
                    url, HttpMethod.GET, new HttpEntity<>(createHeaders()), MovieDetailsDTO.class);
            return response.getBody();
        }catch (RestClientException e) {
            logger.error("Error fetching movie details from TMDB for movie ID " + movieId, e);
            throw new RuntimeException("Error fetching movie details from TMDB", e);
        }
    }

    private List<CastDTO> fetchCastDetails(Long movieId, String language) {
        try {
            String url = buildCreditsUrl(movieId, language);
            ResponseEntity<CreditsResponse> response = restTemplate.exchange(
                    url, HttpMethod.GET, new HttpEntity<>(createHeaders()), CreditsResponse.class);

            return response.getBody().getCast();
        }catch (RestClientException e) {
            logger.error("Error fetching cast details from TMDB for movie ID " + movieId, e);
            throw new RuntimeException("Error fetching cast details from TMDB", e);
        }

    }

    private List<CrewDTO> fetchCrewDetails(Long movieId, String language) {
        try {
            String url = buildCreditsUrl(movieId, language);
            ResponseEntity<CreditsResponse> response = restTemplate.exchange(
                    url, HttpMethod.GET, new HttpEntity<>(createHeaders()), CreditsResponse.class);

            return response.getBody().getCrew();
        }catch (RestClientException e) {
        logger.error("Error fetching crew details from TMDB for movie ID " + movieId, e);
        throw new RuntimeException("Error fetching crew details from TMDB", e);
    }
    }

    private String fetchTrailerKey(Long movieId, String language) {
        try{
            String url = UriComponentsBuilder.fromHttpUrl(tmdbBaseUrl + "/movie/" + movieId + "/videos")
                    .queryParam("language", language)
                    .toUriString();

            ResponseEntity<VideosResponse> response = restTemplate.exchange(
                    url, HttpMethod.GET, new HttpEntity<>(createHeaders()), VideosResponse.class);

            return response.getBody().getResults().stream()
                    .filter(video -> "Trailer".equals(video.getType()))
                    .findFirst()
                    .map(VideoDTO::getKey)
                    .orElse(null);
        } catch (RestClientException e) {
            logger.error("Error fetching trailer key from TMDB for movie ID " + movieId, e);
            throw new RuntimeException("Error fetching trailer key from TMDB", e);
        }
    }

    private List<String> fetchImagePaths(Long movieId) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(tmdbBaseUrl + "/movie/" + movieId + "/images")
                    .toUriString();

            ResponseEntity<ImagesResponse> response = restTemplate.exchange(
                    url, HttpMethod.GET, new HttpEntity<>(createHeaders()), ImagesResponse.class);

            if (response.getBody() != null && response.getBody().getBackdrops() != null) {
                return response.getBody().getBackdrops().stream()
                        .map(ImagesResponse.Image::getFilePath)
                        .collect(Collectors.toList());
            } else {
                return Collections.emptyList();
            }
        }catch (RestClientException e) {
            logger.error("Error fetching image paths from TMDB for movie ID " + movieId, e);
            throw new RuntimeException("Error fetching image paths from TMDB", e);
        }
    }


    private String buildCreditsUrl(Long movieId, String language) {
        return UriComponentsBuilder.fromHttpUrl(tmdbBaseUrl + "/movie/" + movieId + "/credits")
                .queryParam("language", language)
                .toUriString();
    }

    public List<MovieSummaryDTO> getMoviesByCategory(String category, String language, int page) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(tmdbBaseUrl + "/movie/" + category)
                    .queryParam("language", language)
                    .queryParam("page", page)
                    .toUriString();

            ResponseEntity<MovieListApiResponse> response = restTemplate.exchange(
                    url, HttpMethod.GET, new HttpEntity<>(createHeaders()), MovieListApiResponse.class);

            return response.getBody().getResults().stream()
                    .map(this::convertToMovieSummaryDTO)
                    .collect(Collectors.toList());
        } catch (RestClientException e) {
        logger.error("Error fetching movies by category from TMDB: " + category, e);
        throw new RuntimeException("Error fetching movies by category from TMDB", e);
    }
    }
    public List<MovieSummaryDTO> getMoviesBySearch(String query, String language, int page) {
        try{
            String url = UriComponentsBuilder.fromHttpUrl(tmdbBaseUrl + "/search/movie")
                    .queryParam("query", query)
                    .queryParam("language", language)
                    .queryParam("page", page)
                    .toUriString();

            ResponseEntity<MovieListApiResponse> response = restTemplate.exchange(
                    url, HttpMethod.GET, new HttpEntity<>(createHeaders()), MovieListApiResponse.class);

            return response.getBody().getResults().stream()
                    .map(this::convertToMovieSummaryDTO)
                    .collect(Collectors.toList());
        } catch (RestClientException e) {
            logger.error("Error searching movies from TMDB with query: " + query, e);
            throw new RuntimeException("Error searching movies from TMDB", e);
        }
    }
    private MovieSummaryDTO convertToMovieSummaryDTO(MovieSummaryResponse movieSummaryResponse) {
        try {
            MovieSummaryDTO dto = new MovieSummaryDTO();
            dto.setId(movieSummaryResponse.getId());
            dto.setOriginalTitle(movieSummaryResponse.getOriginalTitle());
            dto.setPosterPath(movieSummaryResponse.getPosterPath());
            dto.setVoteAverage(movieSummaryResponse.getVoteAverage());
            if (movieSummaryResponse.getReleaseDate() != null && !movieSummaryResponse.getReleaseDate().isEmpty()) {
                dto.setReleaseDate(LocalDate.parse(movieSummaryResponse.getReleaseDate()));
            } else {
                dto.setReleaseDate(null);
            }
            return dto;
        } catch (Exception e) {
            logger.error("Error converting MovieSummary to MovieSummaryDTO for movie ID: " + movieSummaryResponse.getId(), e);
            throw new RuntimeException("Error converting MovieSummary to MovieSummaryDTO", e);
        }
    }

}

