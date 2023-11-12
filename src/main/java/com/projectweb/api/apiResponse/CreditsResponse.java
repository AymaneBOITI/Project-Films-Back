package com.projectweb.api.apiResponse;

import com.projectweb.api.dto.CastDTO;
import com.projectweb.api.dto.CrewDTO;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CreditsResponse {
    private List<CastDTO> cast;
    private List<CrewDTO> crew;
}
