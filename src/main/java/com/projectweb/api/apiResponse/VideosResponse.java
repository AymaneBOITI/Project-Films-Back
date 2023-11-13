package com.projectweb.api.apiResponse;

import com.projectweb.api.dto.VideoDTO;
import lombok.Data;
import java.util.List;

@Data
public class VideosResponse {
    private List<VideoDTO> results;
}
