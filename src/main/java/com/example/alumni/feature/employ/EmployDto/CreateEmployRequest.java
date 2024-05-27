package com.example.alumni.feature.employ.EmployDto;

import lombok.Builder;

@Builder
public record CreateEmployRequest(
        String employType
) {
}
