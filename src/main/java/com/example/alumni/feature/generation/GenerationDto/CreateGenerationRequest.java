package com.example.alumni.feature.generation.GenerationDto;

import lombok.Builder;

@Builder
public record CreateGenerationRequest(
        String genType,
        Integer numGen
) {
}
