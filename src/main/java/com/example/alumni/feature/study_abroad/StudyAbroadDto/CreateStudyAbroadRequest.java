package com.example.alumni.feature.study_abroad.StudyAbroadDto;

import lombok.Builder;

@Builder
public record CreateStudyAbroadRequest(
        String country
) {
}
