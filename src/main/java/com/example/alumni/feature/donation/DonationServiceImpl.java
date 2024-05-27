package com.example.alumni.feature.donation;

import com.example.alumni.domain.*;
import com.example.alumni.feature.donation.dto.DonationRequest;
import com.example.alumni.feature.donation.dto.DonationResponse;
import com.example.alumni.feature.repo.DonationTypeRepository;

import com.example.alumni.feature.repo.SupportTypeRepository;
import com.example.alumni.feature.user.UserRepository;
import com.example.alumni.mapper.DonationMapper;
import com.example.alumni.utils.CustomPageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    private final DonationTypeRepository donationTypeRepository;
    private final SupportTypeRepository supportTypeRepository;
    private final DonationMapper donationMapper;



    public CustomPageUtils<DonationResponse> CustomPagination(Page<DonationResponse> page, String baseUrl){
        CustomPageUtils<DonationResponse> customPage = new CustomPageUtils<>();
        if(page.hasNext()){
            customPage.setNext(baseUrl + "?page=" + (page.getNumber() + 1) + "&size=" + page.getSize());
        }
        if (page.hasPrevious()){
            customPage.setPrevious(baseUrl + "?page=" + (page.getNumber() - 1) + "&size=" + page.getSize());
        }
        customPage.setTotal((int) page.getTotalElements());
        customPage.setResults(page.getContent());
        return customPage;
    }

    @Override
    public CustomPageUtils<DonationResponse> getAllDonations(int page, int size, String baseUrl) {

        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Page<Donation> donations = donationRepository.findAll(pageable);

        return CustomPagination(donations.map(donationMapper::toDonationResponse), baseUrl);
    }

    @Override
    public DonationResponse getDonationById(String id) {
        Donation donation = donationRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Donation with id " + id + " not found")
        );

        return donationMapper.toDonationResponse(donation);
    }

    @Override
    public DonationResponse createDonation(DonationRequest donationRequest) {
        Donation donation = donationMapper.toDonation(donationRequest);

        User user = userRepository.findById(donationRequest.userId()).orElseThrow(
                () -> new NoSuchElementException("User with id " + donationRequest.userId() + " not found")
        );

        DonationType donationType = donationTypeRepository.findById(donationRequest.donationTypeId()).orElseThrow(
                () -> new NoSuchElementException("DonationType with id " + donationRequest.donationTypeId() + " not found")
        );

       SupportType supportType = supportTypeRepository.findById(donationRequest.supportTypeId()).orElseThrow(
                () -> new NoSuchElementException("SupportType with id " + donationRequest.supportTypeId() + " not found")
        );

        donation.setUser(user);
        donation.setDonationType(donationType);
        donation.setSupportType(supportType);

        donationRepository.save(donation);
        return donationMapper.toDonationResponse(donation);
    }

    @Override
    public DonationResponse updateDonation(String id, DonationRequest donationRequest) {

        Donation donation = donationRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Donation with id " + id + " not found")
        );

        User user = userRepository.findById(donationRequest.userId()).orElseThrow(
                () -> new NoSuchElementException("User with id " + donationRequest.userId() + " not found")
        );

        DonationType donationType = donationTypeRepository.findById(donationRequest.donationTypeId()).orElseThrow(
                () -> new NoSuchElementException("DonationType with id " + donationRequest.donationTypeId() + " not found")
        );

        SupportType supportType = supportTypeRepository.findById(donationRequest.supportTypeId()).orElseThrow(
                () -> new NoSuchElementException("SupportType with id " + donationRequest.supportTypeId() + " not found")
        );

        donation.setUser(user);
        donation.setDonationType(donationType);
        donation.setSupportType(supportType);
        donation.setAmount(donationRequest.amount());
        donation.setThumbnail(donationRequest.thumbnail());

        donationRepository.save(donation);
        return donationMapper.toDonationResponse(donation);

    }

    @Override
    public void deleteDonation(String id) {
        Donation donation = donationRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Donation with id " + id + " not found")
        );

        donationRepository.delete(donation);
    }
}
