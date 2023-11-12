package com.projectweb.api.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class VideosResponse {
    private List<Video> results;
}
