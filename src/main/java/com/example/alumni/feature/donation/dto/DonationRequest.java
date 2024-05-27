package com.example.alumni.feature.donation.dto;

import lombok.Builder;

@Builder
public record DonationRequest(
        String donationTypeId,
        Integer amount,
//        String eventId,
        String thumbnail,
        String supportTypeId,
        String userId
) {
}
