package com.example.alumni.feature.repo;

import com.example.alumni.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, String> {
    AccountType findByName(String name);
}
