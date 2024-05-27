package com.example.alumni.feature.cv_upload.dto;

import lombok.Builder;

@Builder
public record CVUploadResponse(

        String downloadUrl,
        String fileType, float size,
        String filename, String fullUrl,
        String content
) {
}
