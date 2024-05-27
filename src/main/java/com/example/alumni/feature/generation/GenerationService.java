package com.example.alumni.feature.generation;

import com.example.alumni.feature.generation.GenerationDto.CreateGenerationRequest;
import com.example.alumni.feature.generation.GenerationDto.GenerationResponse;
import com.example.alumni.utils.CustomPageUtils;

public interface GenerationService {

    GenerationResponse createGeneration(CreateGenerationRequest request);

    GenerationResponse updateGeneration(String id, CreateGenerationRequest request);

    void deleteGeneration(String id);

    CustomPageUtils<GenerationResponse> getGeneration(int page, int size,String baseUrl);

}
