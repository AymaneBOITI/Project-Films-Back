package com.projectweb.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDTO {
    @JsonProperty("file_path")
    private String filePath;
}