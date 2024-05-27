package com.example.alumni.mapper;

import com.example.alumni.domain.Generation;
import com.example.alumni.feature.generation.GenerationDto.CreateGenerationRequest;
import com.example.alumni.feature.generation.GenerationDto.GenerationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenerationMapper {

    GenerationResponse toGenerationResponse(Generation generation);

    Generation requestToGeneration(CreateGenerationRequest request);

}
