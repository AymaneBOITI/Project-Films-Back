package com.projectweb.api.apiResponse;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.projectweb.api.dto.ImageDTO;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ImagesResponse {
    private List<ImageDTO> backdrops;
}
