package com.example.alumni.feature.employ.EmployDto;

import lombok.Builder;

@Builder
public record EmployResponse(
        String id,
        String employType
) {
}
