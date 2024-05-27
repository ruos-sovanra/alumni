package com.example.alumni.feature.donation.dto;

import lombok.Builder;

@Builder
public record DonationResponse(
        String id,
        String donationTypeName,
        Integer amount,
        String thumbnail,
        String supportTypeName,
        String userName
) {
}
