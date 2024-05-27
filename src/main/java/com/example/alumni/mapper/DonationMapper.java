package com.example.alumni.mapper;

import com.example.alumni.domain.Donation;
import com.example.alumni.feature.donation.dto.DonationRequest;
import com.example.alumni.feature.donation.dto.DonationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DonationMapper {

    @Mapping(target = "donationTypeName", source = "donationType.donationType")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "supportTypeName", source = "supportType.supportType")
    DonationResponse toDonationResponse(Donation donation);

    Donation toDonation(DonationRequest donationRequest);
}
