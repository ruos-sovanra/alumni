package com.example.alumni.feature.job.JobDto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;

@Builder
public record CreateJobRequest(
        String jobTitle,
        String jobDescription,
        String jobLocation,
        String position,
        String companyName,
        BigDecimal salary,
        String userId,
        String poster,
        String statusCode,
        Map<String, Object> requirements
) {
}
