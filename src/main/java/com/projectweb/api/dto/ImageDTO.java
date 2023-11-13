package com.projectweb.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class ImageDTO {
    @JsonProperty("file_path")
    private String filePath;
}