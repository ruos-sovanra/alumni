package com.example.alumni.feature.social.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record PostResponse(
        String id,
        String caption,
        List<Thumbnail> thumbnails,
        LocalDate created_at,
        Integer likes,
        Integer shares,
        String postTypeName
) {}