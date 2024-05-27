package com.example.alumni.feature.user_detail;


import com.example.alumni.domain.*;
import com.example.alumni.feature.user_detail.dto.AdminUpdateRequest;
import com.example.alumni.feature.user_detail.dto.UserDetailRequest;
import com.example.alumni.feature.user_detail.dto.UserDetailResponse;
import com.example.alumni.feature.employ.EmployRepository;
import com.example.alumni.feature.generation.GenerationRepository;
import com.example.alumni.feature.study_abroad.StudyAbroadRepository;
import com.example.alumni.feature.user.UserRepository;
import com.example.alumni.mapper.UserDetailMapper;
import com.example.alumni.utils.CustomPageUtils;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileDetailServiceImpl implements UserDetailService{

    private final UserDetailRepository userDetailRepository;
    private final UserRepository userRepository;
    private final GenerationRepository generationRepository;
    private final StudyAbroadRepository studyAbroadRepository;
    private final EmployRepository employTypeRepository;
    private final UserDetailMapper userDetailMapper;


    @Override
    public CustomPageUtils<UserDetailResponse> getAllUserDetails(int page, int size, String baseUrl, Optional<String> genType, Optional<String> numGen, Optional<Boolean> isGraduated, Optional<Boolean> isEmployed, Optional<String> country, Optional<String> employType) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Specification<UserDetail> spec = Specification.where(genType.isPresent() ? UserDetailSpecification.hasGenType(genType.get()) : null)
                .and(numGen.isPresent() ? UserDetailSpecification.hasGenNum(numGen.get()) : null).and(isGraduated.isPresent() ? UserDetailSpecification.isGraduated(isGraduated.get()) : null)
                .and(isEmployed.isPresent() ? UserDetailSpecification.isEmployed(isEmployed.get()) : null).and(country.isPresent() ? UserDetailSpecification.hasStudyAbroadCountry(country.get()) : null)
                .and(employType.isPresent() ? UserDetailSpecification.hasEmployType(employType.get()) : null);
        Page<UserDetail> userDetailPage = userDetailRepository.findAll(spec,pageable);
        return CustomPagination(userDetailPage.map(userDetailMapper::toUserDetailResponse), baseUrl);
    }

    @Override
    public void deleteUserDetail(String id) {
        userDetailRepository.deleteById(id);
    }

    public CustomPageUtils<UserDetailResponse> CustomPagination(Page<UserDetailResponse> page, String baseUrl) {
        CustomPageUtils<UserDetailResponse> customPage = new CustomPageUtils<>();

        if (page != null && page.hasPrevious()) {
            customPage.setPrevious(baseUrl + "?page=" + (page.getNumber() -1) + "&size=" + page.getSize());
        }

        if (page != null && page.hasNext()) {
            customPage.setNext(baseUrl + "?page=" + (page.getNumber() + 1) + "&size=" + page.getSize());
        }

        if (page != null) {
            customPage.setTotal(page.getTotalPages());
            customPage.setResults(page.getContent());
        }

        return customPage;
    }


    @Override
    public UserDetailResponse createUserDetail(UserDetailRequest userDetailRequest) {
        UserDetail userDetail = userDetailMapper.toUserDetail(userDetailRequest);
        User user = userRepository.findById(userDetailRequest.userId()).orElseThrow(
                () -> new NoSuchElementException("User not found")

        );
        userDetail.setUser(user);
        userDetail.setIsEmployed(false);
        userDetail.setIsGraduated(false);
        userDetailRepository.save(userDetail);
        return userDetailMapper.toUserDetailResponse(userDetail);
    }

    @Override
    public UserDetailResponse updateUserDetail(String id, UserDetailRequest userDetailRequest) {
        UserDetail userDetail = userDetailRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("UserDetail not found")
        );
        User user = userRepository.findById(userDetailRequest.userId()).orElseThrow(
                () -> new NoSuchElementException("User not found")
        );

        userDetail.setUser(user);
        userDetail.setAddress(userDetailRequest.address());
        userDetail.setAchievements(userDetailRequest.achievements());
        userDetail.setEducations(userDetailRequest.educations());
        userDetail.setWorkExperiences(userDetailRequest.workExperiences());
        userDetail.setSkills(userDetailRequest.skills());
        userDetail.setInterests(userDetailRequest.interests());
        userDetail.setLanguages(userDetailRequest.languages());
        userDetail.setFirstName(userDetailRequest.firstName());
        userDetail.setLastName(userDetailRequest.lastName());
        userDetail.setTelephone(userDetailRequest.telephone());
        userDetail.setEmail(userDetailRequest.email());
        userDetail.setGender(userDetailRequest.gender());
        userDetail.setNationality(userDetailRequest.nationality());
        userDetailRepository.save(userDetail);
        return userDetailMapper.toUserDetailResponse(userDetail);
    }

    @Override
    public UserDetailResponse adminUpdateUserDetail(String id, AdminUpdateRequest adminUpdateRequest) {

        UserDetail userDetail = userDetailRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("UserDetail not found")
        );

        Generation generation = generationRepository.findById(adminUpdateRequest.generationId()).orElseThrow(
                () -> new NoSuchElementException("Generation not found")
        );

        Employ employType = employTypeRepository.findByEmployType(adminUpdateRequest.employTypeName()).orElseThrow(
                () -> new NoSuchElementException("EmployType not found")

        );
        StudyAbroad studyAbroad = studyAbroadRepository.findByCountry(adminUpdateRequest.countryName()).orElseThrow(
                () -> new NoSuchElementException("StudyAbroad not found")

        );

        userDetail.setEmploy(employType);
        userDetail.setGeneration(generation);
        userDetail.setStudyAbroad(studyAbroad);
        userDetail.setIsGraduated(adminUpdateRequest.isGraduated());
        userDetail.setIsEmployed(adminUpdateRequest.isEmployed());
        userDetailRepository.save(userDetail);

        return userDetailMapper.toUserDetailResponse(userDetail);
    }

    @Override
    public void generateCSVReport(OutputStream out) {
        List<UserDetail> userDetails = userDetailRepository.findAll();

        try {
            Writer writer = new OutputStreamWriter(out);
            CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

            String[] headerRecord = {"ID", "First Name", "Last Name", "Email", "Telephone"};
            csvWriter.writeNext(headerRecord);

            for (UserDetail userDetail : userDetails) {
                csvWriter.writeNext(new String[]{
                        userDetail.getId(),
                        userDetail.getFirstName(),
                        userDetail.getLastName(),
                        userDetail.getEmail(),
                        userDetail.getTelephone()
                });
            }

            csvWriter.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }


}
