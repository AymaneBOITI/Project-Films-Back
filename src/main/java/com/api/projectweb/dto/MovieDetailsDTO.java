package com.api.projectweb.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieDetailsDTO {
    private Long id;
    private String title;
    private Integer runtime;
    private String originalLanguage;
    private List<GenreDTO> genres;
    private List<ActorsDTO> cast;
    private List<CrewDTO> crew;
    private String summary;
    private String trailer;
}