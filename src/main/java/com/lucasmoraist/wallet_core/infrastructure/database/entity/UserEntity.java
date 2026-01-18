package com.lucasmoraist.wallet_core.infrastructure.database.entity;

import com.lucasmoraist.wallet_core.domain.enums.RolesEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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
    @Column(name = "type_user")
    private RolesEnum role;

    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private WalletEntity wallet;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
