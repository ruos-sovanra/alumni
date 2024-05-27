package com.example.alumni.feature.user_detail.dto;

public record AdminUpdateRequest(
        String generationId,
        String countryName,
        String employTypeName,
        Boolean isGraduated,
        Boolean isEmployed
) {
}
