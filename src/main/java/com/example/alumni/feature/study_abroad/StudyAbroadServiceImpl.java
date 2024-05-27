package com.example.alumni.feature.study_abroad;

import com.example.alumni.domain.StudyAbroad;
import com.example.alumni.feature.study_abroad.StudyAbroadDto.CreateStudyAbroadRequest;
import com.example.alumni.feature.study_abroad.StudyAbroadDto.StudyAbroadResponse;
import com.example.alumni.mapper.StudyAbroadMapper;
import com.example.alumni.utils.CustomPageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StudyAbroadServiceImpl implements StudyAbroadService{

    private final StudyAbroadRepository studyAbroadRepository;
    private final StudyAbroadMapper studyAbroadMapper;

    @Override
    public StudyAbroadResponse createStudyAbroad(CreateStudyAbroadRequest request) {

        StudyAbroad studyAbroad = studyAbroadMapper.requestToStudyAbroad(request);

        studyAbroadRepository.save(studyAbroad);

        return studyAbroadMapper.toStudyAbroadResponse(studyAbroad);
    }

    @Override
    public StudyAbroadResponse updateStudyAbroad(String id, CreateStudyAbroadRequest request) {

        StudyAbroad studyAbroad = studyAbroadRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Study Abroad not found")
        );

        studyAbroad.setCountry(request.country());
        studyAbroadRepository.save(studyAbroad);

        return studyAbroadMapper.toStudyAbroadResponse(studyAbroad);
    }

    @Override
    public void deleteStudyAbroad(String id) {
        studyAbroadRepository.deleteById(id);
    }

    @Override
    public CustomPageUtils<StudyAbroadResponse> getStudyAbroad(int page, int size, String baseUrl) {

        Pageable pageable = PageRequest.of(page, size);

        Page<StudyAbroad> studyAbroad = studyAbroadRepository.findAll(pageable);

        return CustomPagination(studyAbroad.map(studyAbroadMapper::toStudyAbroadResponse), baseUrl);
    }

    public CustomPageUtils<StudyAbroadResponse> CustomPagination(Page<StudyAbroadResponse> page, String baseUrl){
        CustomPageUtils<StudyAbroadResponse> customPage = new CustomPageUtils<>();

        if(page.hasNext()){
            customPage.setNext(baseUrl + "?page=" + (page.getNumber() + 1) + "&size=" + page.getSize());
        }

        if (page.hasPrevious()){
            customPage.setPrevious(baseUrl + "?page=" + (page.getNumber() - 1) + "&size=" + page.getSize());
        }

        customPage.setTotal((int) page.getTotalElements());

        customPage.setResults(page.getContent());

        return customPage;
    }
}
