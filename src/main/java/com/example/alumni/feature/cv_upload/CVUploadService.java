package com.example.alumni.feature.cv_upload;


import com.example.alumni.feature.cv_upload.dto.CVUploadResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CVUploadService {
    CVUploadResponse uploadSingleFile(MultipartFile file, HttpServletRequest request) throws IOException;
    List<String> getAllFileNames();
    ResponseEntity<Resource> serveFile(String filename, HttpServletRequest request);
}
