package com.example.alumni.feature.user_detail.dto;


import com.example.alumni.feature.generation.GenerationDto.GenerationResponse;
import lombok.Builder;

import java.util.Map;

@Builder
public record UserDetailResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        String gender,
        String nationality,
        String address,
        String telephone,
        Map<String, Object> educations,
        Map<String, Object> workExperiences,
        Map<String, Object> interests,
        Map<String, Object> achievements,
        Map<String, Object> skills,
        Map<String, Object> languages,
        GenerationResponse generation,
        String employTypeName,
        String studyAbroadName,
        String userName

) {
}
