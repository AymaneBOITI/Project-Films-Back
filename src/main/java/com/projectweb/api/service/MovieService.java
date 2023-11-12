package com.projectweb.api.service;
import com.projectweb.api.dto.*;

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

    @Value("${tmdb.api.baseurl}")
    private String tmdbBaseUrl;

    @Value("${tmdb.bearer.token}")
    private String bearerToken;

    private final RestTemplate restTemplate;

    public MovieService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MovieDetailsDTO getMovieDetails(Long movieId, String language) {
        MovieDetailsDTO movieDetails = fetchMovieDetails(movieId, language);
        movieDetails.setCast(fetchCastDetails(movieId, language));
        movieDetails.setCrew(fetchCrewDetails(movieId, language));
        movieDetails.setTrailerKey(fetchTrailerKey(movieId, language));
        movieDetails.setImagePaths(fetchImagePaths(movieId));
        return movieDetails;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken);
        return headers;
    }

    private MovieDetailsDTO fetchMovieDetails(Long movieId, String language) {
        String url = UriComponentsBuilder.fromHttpUrl(tmdbBaseUrl + "/movie/" + movieId)
                .queryParam("language", language)
                .toUriString();

        ResponseEntity<MovieDetailsDTO> response = restTemplate.exchange(
                url, HttpMethod.GET, new HttpEntity<>(createHeaders()), MovieDetailsDTO.class);

        return response.getBody();
    }

    private List<CastDTO> fetchCastDetails(Long movieId, String language) {
        String url = buildCreditsUrl(movieId, language);
        ResponseEntity<CreditsResponse> response = restTemplate.exchange(
                url, HttpMethod.GET, new HttpEntity<>(createHeaders()), CreditsResponse.class);

        return response.getBody().getCast();
    }

    private List<CrewDTO> fetchCrewDetails(Long movieId, String language) {
        String url = buildCreditsUrl(movieId, language);
        ResponseEntity<CreditsResponse> response = restTemplate.exchange(
                url, HttpMethod.GET, new HttpEntity<>(createHeaders()), CreditsResponse.class);

        return response.getBody().getCrew();
    }

    private String fetchTrailerKey(Long movieId, String language) {
        String url = UriComponentsBuilder.fromHttpUrl(tmdbBaseUrl + "/movie/" + movieId + "/videos")
                .queryParam("language", language)
                .toUriString();

        ResponseEntity<VideosResponse> response = restTemplate.exchange(
                url, HttpMethod.GET, new HttpEntity<>(createHeaders()), VideosResponse.class);

        return response.getBody().getResults().stream()
                .filter(video -> "Trailer".equals(video.getType()))
                .findFirst()
                .map(Video::getKey)
                .orElse(null);
    }

    private List<String> fetchImagePaths(Long movieId) {
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
    }


    private String buildCreditsUrl(Long movieId, String language) {
        return UriComponentsBuilder.fromHttpUrl(tmdbBaseUrl + "/movie/" + movieId + "/credits")
                .queryParam("language", language)
                .toUriString();
    }

    public List<MovieSummaryDTO> getMoviesByCategory(String category, String language, int page) {
        String url = UriComponentsBuilder.fromHttpUrl(tmdbBaseUrl + "/movie/" + category)
                .queryParam("language", language)
                .queryParam("page", page)
                .toUriString();

        ResponseEntity<MovieListApiResponse> response = restTemplate.exchange(
                url, HttpMethod.GET, new HttpEntity<>(createHeaders()), MovieListApiResponse.class);

        return response.getBody().getResults().stream()
                .map(this::convertToMovieSummaryDTO)
                .collect(Collectors.toList());
    }
    public List<MovieSummaryDTO> getMoviesBySearch(String query, String language, int page) {
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
    }
    private MovieSummaryDTO convertToMovieSummaryDTO(MovieSummary movieSummary) {
        MovieSummaryDTO dto = new MovieSummaryDTO();
        dto.setId(movieSummary.getId());
        dto.setOriginalTitle(movieSummary.getOriginalTitle());
        dto.setPosterPath(movieSummary.getPosterPath());
        dto.setVoteAverage(movieSummary.getVoteAverage());
        if (movieSummary.getReleaseDate() != null && !movieSummary.getReleaseDate().isEmpty()) {
            dto.setReleaseDate(LocalDate.parse(movieSummary.getReleaseDate()));
        } else {
            dto.setReleaseDate(null);
        }
        return dto;
    }

}

