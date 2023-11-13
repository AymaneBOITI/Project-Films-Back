package com.projectweb.api.apiResponse;

import lombok.Data;

import java.util.List;

@Data
public class MovieListApiResponse {
    private List<MovieSummaryResponse> results;

}