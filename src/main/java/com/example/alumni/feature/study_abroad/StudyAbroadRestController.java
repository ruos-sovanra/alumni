package com.example.alumni.feature.study_abroad;


import com.example.alumni.feature.study_abroad.StudyAbroadDto.CreateStudyAbroadRequest;
import com.example.alumni.feature.study_abroad.StudyAbroadDto.StudyAbroadResponse;
import com.example.alumni.utils.BaseResponse;
import com.example.alumni.utils.CustomPageUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/study-abroad")
public class StudyAbroadRestController {

    private final StudyAbroadService studyAbroadService;


    @GetMapping
    @Operation(summary = "Get all study abroad")
    public ResponseEntity<CustomPageUtils<StudyAbroadResponse>> getAllStudyAbroad(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/api/v1/study-abroad";
        CustomPageUtils<StudyAbroadResponse> studyAbroadResponses = studyAbroadService.getStudyAbroad(page, size, baseUrl);
        return ResponseEntity.ok(studyAbroadResponses);
    }

    @PostMapping
    @Operation(summary = "Create study abroad")
    public BaseResponse<StudyAbroadResponse> createStudyAbroad(@RequestBody CreateStudyAbroadRequest request) {
        return BaseResponse.<StudyAbroadResponse>createSuccess()
                .setPayload(studyAbroadService.createStudyAbroad(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update study abroad")
    public BaseResponse<StudyAbroadResponse> updateStudyAbroad(@PathVariable String id, @RequestBody CreateStudyAbroadRequest request) {
        return BaseResponse.<StudyAbroadResponse>updateSuccess()
                .setPayload(studyAbroadService.updateStudyAbroad(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete study abroad")
    public BaseResponse<Void> deleteStudyAbroad(@PathVariable String id) {
        studyAbroadService.deleteStudyAbroad(id);
        return BaseResponse.deleteSuccess();
    }

}
