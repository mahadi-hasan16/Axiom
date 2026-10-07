package com.axiom.api.adapter.out.persistence.jpa.repository;

import com.axiom.api.adapter.out.persistence.jpa.entity.AccountEntity;
import com.axiom.api.domain.account.model.AccountId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
    Optional<AccountEntity> findByAccountNumber(String accountNumber);

    boolean existsByAccountNumber(String accountNumber);

    List<AccountEntity> findByClassification(String classification);
}
