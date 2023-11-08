package com.api.projectweb.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieListDTO {
    private int page;
    private List<MovieDTO> results;
    private int totalResults;
    private int totalPages;
}
