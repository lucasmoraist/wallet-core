package com.lucasmoraist.wallet_core.infrastructure.database.entity;

import com.lucasmoraist.wallet_core.domain.enums.RolesEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_users")
@Entity(name = "t_users")
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String fullName;
    private String cpfCnpj;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private RolesEnum role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WalletEntity> wallets = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
