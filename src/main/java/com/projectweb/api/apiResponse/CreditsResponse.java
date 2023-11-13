package com.projectweb.api.apiResponse;

import com.projectweb.api.dto.CastDTO;
import com.projectweb.api.dto.CrewDTO;
import lombok.Data;
import java.util.List;

@Data
public class CreditsResponse {
    private List<CastDTO> cast;
    private List<CrewDTO> crew;
}
