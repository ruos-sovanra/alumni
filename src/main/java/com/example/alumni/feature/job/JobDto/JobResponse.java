package com.example.alumni.feature.job.JobDto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;

@Builder
public record JobResponse(
        String id,
        String jobTitle,
        String jobDescription,
        String jobLocation,
        String position,
        String companyName,
        BigDecimal salary,
        String userId,
        String poster,
        Map<String, Object> requirements
) {
}
