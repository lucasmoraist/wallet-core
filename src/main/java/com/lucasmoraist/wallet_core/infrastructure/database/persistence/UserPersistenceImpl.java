package com.lucasmoraist.wallet_core.infrastructure.database.persistence;

import com.lucasmoraist.wallet_core.application.gateway.UserPersistence;
import com.lucasmoraist.wallet_core.domain.model.User;
import com.lucasmoraist.wallet_core.infrastructure.database.entity.UserEntity;
import com.lucasmoraist.wallet_core.infrastructure.database.repository.UserRepository;
import com.lucasmoraist.wallet_core.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Component
@RequiredArgsConstructor
public class UserPersistenceImpl implements UserPersistence {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User save(User user) {
        log.debug("Saving user: {}", user);
        UserEntity entity = UserMapper.toEntity(user);
        UserEntity userSaved = this.userRepository.save(entity);
        return UserMapper.toDomain(userSaved);
    }

}
