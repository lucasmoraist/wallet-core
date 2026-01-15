package com.lucasmoraist.wallet_core.infrastructure.database.repository;

import com.lucasmoraist.wallet_core.infrastructure.database.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, UUID> {

}
