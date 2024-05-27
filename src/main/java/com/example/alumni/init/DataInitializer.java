package com.example.alumni.init;

import com.example.alumni.domain.*;
import com.example.alumni.feature.repo.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final PostRepository postRepository;
    private final StatusRepository statusRepository;
    private final DonationTypeRepository donationTypeRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final RoleRepository roleRepository;
    private final SupportTypeRepository supportTypeRepository;

    @PostConstruct
    void init(){
        postTypeInit();
        statusInit();
        donationTypeInit();
        accountTypeInit();
        roleInit();
        supportTypeInit();
    }

    void postTypeInit(){
        List<String> postTypes = List.of("EVENT","JOB","SOCIAL","DONATION");
        if (postRepository.count() == 0){
            postTypes.forEach(postType -> {
                Post newPostType = new Post();
                newPostType.setType(postType);
                postRepository.save(newPostType);
            });
        }
    }

    void statusInit(){
        List<String> statuses = List.of("PENDING","APPROVED","REJECTED");
        if (statusRepository.count() == 0){
            statuses.forEach(status -> {
                Status newStatus = new Status();
                newStatus.setStatus(status);
                statusRepository.save(newStatus);
            });
        }
    }

    void donationTypeInit(){
        List<String> donationTypes = List.of("CASH","MATERIAL");
        if (donationTypeRepository.count() == 0){
            donationTypes.forEach(donationType -> {
                DonationType newDonationType = new DonationType();
                newDonationType.setDonationType(donationType);
                donationTypeRepository.save(newDonationType);
            });
        }
    }

    void accountTypeInit(){
        List<String> accountTypes = List.of("ALUMNI","PARTNER");
        if (accountTypeRepository.count() == 0){
            accountTypes.forEach(accountType -> {
                AccountType newAccountType = new AccountType();
                newAccountType.setName(accountType);
                accountTypeRepository.save(newAccountType);
            });
        }
    }

    void roleInit(){
        List<String> roles = List.of("ADMIN","USER","ALUMNI","PARTNER");
        if (roleRepository.count() == 0){
            roles.forEach(role -> {
                Role newRole = new Role();
                newRole.setName(role);
                roleRepository.save(newRole);
            });
        }
    }

    void supportTypeInit(){
        List<String> supportTypes = List.of("OLD PEOPLE","CHILDREN","NATURE");
        if (supportTypeRepository.count() == 0){
            supportTypes.forEach(supportType -> {
                SupportType newSupportType = new SupportType();
                newSupportType.setSupportType(supportType);
                supportTypeRepository.save(newSupportType);
            });
        }
    }




}
