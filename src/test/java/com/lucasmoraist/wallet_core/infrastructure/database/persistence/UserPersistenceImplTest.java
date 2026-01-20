package com.lucasmoraist.wallet_core.infrastructure.database.persistence;

import com.lucasmoraist.wallet_core.domain.model.User;
import com.lucasmoraist.wallet_core.factory.UserFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class UserPersistenceImplTest {

    @Autowired
    UserPersistenceImpl userPersistenceImpl;

    @Test
    @DisplayName("Should save user successfully")
    void case01() {
        User user = UserFactory.createUser();

        User savedUser = this.userPersistenceImpl.save(user);

        assertNotNull(savedUser);
        assertNotNull(savedUser.id());
        assertNotNull(savedUser.createdAt());
        assertNotNull(savedUser.updatedAt());
    }

    @Test
    @DisplayName("Should find user by id successfully")
    void case02() {
        User user = UserFactory.createUser();
        User savedUser = this.userPersistenceImpl.save(user);

        User foundUser = this.userPersistenceImpl.findById(savedUser.id());

        assertNotNull(foundUser);
    }

    @Test
    @DisplayName("Should throw exception when user not found by id")
    void case03() {
        assertThrows(EntityNotFoundException.class,
                () -> this.userPersistenceImpl.findById(UUID.randomUUID()));
    }

}