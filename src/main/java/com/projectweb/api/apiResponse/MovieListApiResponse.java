package com.projectweb.api.apiResponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieListApiResponse {
    private List<MovieSummaryResponse> results;

}