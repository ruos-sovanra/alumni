package com.example.alumni.mapper;

import com.example.alumni.domain.StudyAbroad;
import com.example.alumni.feature.study_abroad.StudyAbroadDto.CreateStudyAbroadRequest;
import com.example.alumni.feature.study_abroad.StudyAbroadDto.StudyAbroadResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudyAbroadMapper {

    StudyAbroadResponse toStudyAbroadResponse(StudyAbroad studyAbroad);

    StudyAbroad requestToStudyAbroad(CreateStudyAbroadRequest request);

}
