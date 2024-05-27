package com.example.alumni.feature.donation;

import com.example.alumni.domain.Donation;
import com.example.alumni.domain.DonationType;
import com.example.alumni.domain.SupportType;
import com.example.alumni.domain.User;
import com.example.alumni.feature.donation.dto.DonationRequest;
import com.example.alumni.feature.donation.dto.DonationResponse;
import com.example.alumni.feature.repo.DonationTypeRepository;
import com.example.alumni.feature.repo.SupportTypeRepository;
import com.example.alumni.feature.user.UserRepository;
import com.example.alumni.mapper.DonationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DonationServiceImplTest {

    @Mock
    private DonationRepository donationRepository;

    @Mock
    private DonationMapper donationMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DonationTypeRepository donationTypeRepository;

    @Mock
    private SupportTypeRepository supportTypeRepository;

    @InjectMocks
    private DonationServiceImpl donationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateDonation() {
        DonationRequest donationRequest = new DonationRequest(
                "string1",
                2, // Assuming this is the integer value you want to pass
                "string3",
                "string4",
                "string5"
        );

        Donation donation = new Donation();

        DonationResponse donationResponse = new DonationResponse(
                "string1",
                "string2",
                3, // Assuming this is the integer value you want to pass
                "string4",
                "string5",
                "string6"
        );

        when(userRepository.findById(anyString())).thenReturn(java.util.Optional.of(new User()));
        when(donationTypeRepository.findById(anyString())).thenReturn(java.util.Optional.of(new DonationType()));
        when(supportTypeRepository.findById(anyString())).thenReturn(java.util.Optional.of(new SupportType()));
        when(donationMapper.toDonation(any(DonationRequest.class))).thenReturn(donation);
        when(donationMapper.toDonationResponse(any(Donation.class))).thenReturn(donationResponse);
        when(donationRepository.save(any(Donation.class))).thenReturn(donation);

        DonationResponse result = donationService.createDonation(donationRequest);

        assertEquals(donationResponse, result);
        verify(userRepository, times(1)).findById(anyString());
        verify(donationTypeRepository, times(1)).findById(anyString());
        verify(supportTypeRepository, times(1)).findById(anyString());
        verify(donationMapper, times(1)).toDonation(any(DonationRequest.class));
        verify(donationMapper, times(1)).toDonationResponse(any(Donation.class));
        verify(donationRepository, times(1)).save(any(Donation.class));
    }

    @Test
    public void testUpdateDonation() {
        DonationRequest donationRequest = new DonationRequest(
                "string1",
                2, // Assuming this is the integer value you want to pass
                "string3",
                "string4",
                "string5"
        );

        Donation donation = new Donation();

        DonationResponse donationResponse = new DonationResponse(
                "string1",
                "string2",
                3, // Assuming this is the integer value you want to pass
                "string4",
                "string5",
                "string6"
        );

        when(donationRepository.findById(anyString())).thenReturn(java.util.Optional.of(donation));
        when(userRepository.findById(anyString())).thenReturn(java.util.Optional.of(new User()));
        when(donationTypeRepository.findById(anyString())).thenReturn(java.util.Optional.of(new DonationType()));
        when(supportTypeRepository.findById(anyString())).thenReturn(java.util.Optional.of(new SupportType()));
        when(donationMapper.toDonation(any(DonationRequest.class))).thenReturn(donation);
        when(donationMapper.toDonationResponse(any(Donation.class))).thenReturn(donationResponse);
        when(donationRepository.save(any(Donation.class))).thenReturn(donation);

        DonationResponse result = donationService.updateDonation("id", donationRequest);

        assertEquals(donationResponse, result);
        verify(donationRepository, times(1)).findById(anyString());
        verify(userRepository, times(1)).findById(anyString());
        verify(donationTypeRepository, times(1)).findById(anyString());
        verify(supportTypeRepository, times(1)).findById(anyString());
        verify(donationMapper, times(0)).toDonation(any(DonationRequest.class));
        verify(donationMapper, times(1)).toDonationResponse(any(Donation.class));
        verify(donationRepository, times(1)).save(any(Donation.class));
    }

    @Test
    public void testDeleteDonation() {
        Donation donation = new Donation();

        when(donationRepository.findById(anyString())).thenReturn(java.util.Optional.of(donation));

        donationService.deleteDonation("id");

        verify(donationRepository, times(1)).findById(anyString());
        verify(donationRepository, times(1)).delete(any(Donation.class));
    }
}