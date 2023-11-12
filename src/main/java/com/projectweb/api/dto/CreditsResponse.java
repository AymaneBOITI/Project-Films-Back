package com.projectweb.api.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CreditsResponse {
    private List<CastDTO> cast;
    private List<CrewDTO> crew;
}
