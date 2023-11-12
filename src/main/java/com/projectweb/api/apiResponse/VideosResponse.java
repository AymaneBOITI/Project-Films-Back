package com.projectweb.api.apiResponse;

import com.projectweb.api.dto.VideoDTO;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class VideosResponse {
    private List<VideoDTO> results;
}
