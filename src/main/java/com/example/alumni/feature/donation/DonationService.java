package com.example.alumni.feature.donation;

import com.example.alumni.feature.donation.dto.DonationRequest;
import com.example.alumni.feature.donation.dto.DonationResponse;
import com.example.alumni.utils.CustomPageUtils;

public interface DonationService {
    CustomPageUtils<DonationResponse> getAllDonations(int page, int size, String baseUrl);
    DonationResponse getDonationById(String id);
    DonationResponse createDonation(DonationRequest donationRequest);
    DonationResponse updateDonation(String id, DonationRequest donationRequest);
    void deleteDonation(String id);
}
