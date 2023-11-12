package com.projectweb.api.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ImagesResponse {
    private List<Image> backdrops;

    @Getter
    @Setter
    public static class Image {
        @JsonProperty("file_path")
        private String filePath;
    }
}
