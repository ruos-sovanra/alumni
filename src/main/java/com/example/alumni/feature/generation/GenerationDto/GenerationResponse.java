package com.example.alumni.feature.generation.GenerationDto;

import lombok.Builder;

@Builder
public record GenerationResponse(
        String id,
        String genType,
        Integer numGen){
}
