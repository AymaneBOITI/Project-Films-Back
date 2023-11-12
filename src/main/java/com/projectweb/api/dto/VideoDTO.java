package com.projectweb.api.dto;

import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Schema(description = "Data structure representing a video, such as a trailer")
public class VideoDTO {
    @Schema(description = "Key of the video on its hosting platform")
    private String key;

    @Schema(description = "Type of the video, e.g., Trailer")
    private String type;
}
