package com.example.alumni.feature.study_abroad;

import com.example.alumni.domain.StudyAbroad;
import com.example.alumni.feature.study_abroad.StudyAbroadDto.CreateStudyAbroadRequest;
import com.example.alumni.feature.study_abroad.StudyAbroadDto.StudyAbroadResponse;
import com.example.alumni.mapper.StudyAbroadMapper;
import com.example.alumni.utils.CustomPageUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StudyAbroadServiceImplTest {

    @InjectMocks
    private StudyAbroadServiceImpl studyAbroadService;

    @Mock
    private StudyAbroadRepository studyAbroadRepository;

    @Mock
    private StudyAbroadMapper studyAbroadMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateStudyAbroad() {
        // Prepare data
        CreateStudyAbroadRequest request = new CreateStudyAbroadRequest(
                "country"
        );
        StudyAbroad studyAbroad = new StudyAbroad();
        StudyAbroadResponse response = new StudyAbroadResponse(
                "id",
                "country"
        );

        // Define the behavior of mocked objects
        when(studyAbroadMapper.requestToStudyAbroad(request)).thenReturn(studyAbroad);
        when(studyAbroadRepository.save(studyAbroad)).thenReturn(studyAbroad);
        when(studyAbroadMapper.toStudyAbroadResponse(studyAbroad)).thenReturn(response);

        // Call the method to be tested
        StudyAbroadResponse result = studyAbroadService.createStudyAbroad(request);

        // Verify the result
        assertEquals(response, result);
    }

    @Test
    public void testUpdateStudyAbroad() {
        // Prepare data
        String id = "studyAbroadId";
        CreateStudyAbroadRequest request = new CreateStudyAbroadRequest("newCountry");
        StudyAbroad studyAbroad = new StudyAbroad();
        StudyAbroadResponse response = new StudyAbroadResponse(id, "newCountry");

        // Define the behavior of mocked objects
        when(studyAbroadRepository.findById(id)).thenReturn(Optional.of(studyAbroad));
        when(studyAbroadRepository.save(studyAbroad)).thenReturn(studyAbroad);
        when(studyAbroadMapper.toStudyAbroadResponse(studyAbroad)).thenReturn(response);

        // Call the method to be tested
        StudyAbroadResponse result = studyAbroadService.updateStudyAbroad(id, request);

        // Verify the result
        assertEquals(response, result);
    }

    @Test
    public void testDeleteStudyAbroad() {
        // Prepare data
        String id = "studyAbroadId";

        // Define the behavior of mocked objects
        doNothing().when(studyAbroadRepository).deleteById(id);

        // Call the method to be tested
        studyAbroadService.deleteStudyAbroad(id);

        // Verify the behavior
        verify(studyAbroadRepository, times(1)).deleteById(id);
    }
}