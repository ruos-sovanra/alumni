package com.example.alumni.feature.cv_upload;


import com.example.alumni.feature.cv_upload.dto.CVUploadResponse;
import com.example.alumni.utils.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cv-upload")
@RequiredArgsConstructor
public class CVUploadRestController {

    private final CVUploadService CVUploadService;

    @GetMapping
    public BaseResponse<List<String>> getAllFileNames() {
        return BaseResponse
                .<List<String>>ok()
                .setPayload(CVUploadService.getAllFileNames());
    }

    @PostMapping(value = "", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<CVUploadResponse> uploadSingleFile(
            @RequestPart("file") MultipartFile file, HttpServletRequest request
    ) throws IOException {
        return BaseResponse
                .<CVUploadResponse>ok()
                .setPayload(CVUploadService.uploadSingleFile(file, request));
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        return CVUploadService.serveFile(fileName, request);
    }

}
