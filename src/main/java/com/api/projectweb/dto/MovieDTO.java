package com.api.projectweb.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieDTO {
    private Long id;
    private String title;
    private String releaseDate;
    private String posterPath;
    private List<Integer> genreIds;
    private Double popularity;
    private String overview;
}