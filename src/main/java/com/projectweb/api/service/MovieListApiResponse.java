package com.projectweb.api.service;
import com.projectweb.api.dto.MovieSummaryDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
class MovieListApiResponse {
    private List<MovieSummary> results;

}