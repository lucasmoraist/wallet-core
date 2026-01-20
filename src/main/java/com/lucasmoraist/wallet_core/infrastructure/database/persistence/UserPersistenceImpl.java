package com.lucasmoraist.wallet_core.infrastructure.database.persistence;

import com.lucasmoraist.wallet_core.application.gateway.UserPersistence;
import com.lucasmoraist.wallet_core.domain.model.User;
import com.lucasmoraist.wallet_core.infrastructure.database.entity.UserEntity;
import com.lucasmoraist.wallet_core.infrastructure.database.repository.UserRepository;
import com.lucasmoraist.wallet_core.infrastructure.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
public class UserPersistenceImpl implements UserPersistence {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User save(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        UserEntity userSaved = this.userRepository.saveAndFlush(entity);
        log.debug("User saved with id: {}", userSaved);
        return UserMapper.toDomain(userSaved);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(UUID userId) {
        log.debug("Finding user by id: {}", userId);
        User user = UserMapper.toDomain(findEntityById(userId));
        log.debug("User found: {}", user);
        return user;
    }

    private UserEntity findEntityById(UUID userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", userId);
                    return new EntityNotFoundException("User not found");
                });
    }

}
