package com.example.alumni.feature.study_abroad;

import com.example.alumni.feature.study_abroad.StudyAbroadDto.CreateStudyAbroadRequest;
import com.example.alumni.feature.study_abroad.StudyAbroadDto.StudyAbroadResponse;
import com.example.alumni.utils.CustomPageUtils;

public interface StudyAbroadService {

    StudyAbroadResponse createStudyAbroad(CreateStudyAbroadRequest request);

    StudyAbroadResponse updateStudyAbroad(String id, CreateStudyAbroadRequest request);

    void deleteStudyAbroad(String id);

    CustomPageUtils<StudyAbroadResponse> getStudyAbroad(int page, int size,String baseUrl);
}
