package com.projectweb.api.apiResponse;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.projectweb.api.dto.ImageDTO;
import lombok.Data;
import java.util.List;

@Data
public class ImagesResponse {
    private List<ImageDTO> backdrops;
}
