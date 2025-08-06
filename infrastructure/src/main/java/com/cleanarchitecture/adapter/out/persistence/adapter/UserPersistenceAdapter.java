/*
 *  UserPersistenceAdapter
 *  @author: minhhieuano
 *  @created 8/7/2025 3:24 AM
 * */


package com.cleanarchitecture.adapter.out.persistence.adapter;

import com.cleanarchitecture.adapter.mapper.UserMapper;
import com.cleanarchitecture.adapter.out.persistence.entity.MongoUser;
import com.cleanarchitecture.adapter.out.persistence.repository.UserRepository;
import com.cleanarchitecture.application.port.out.LoadUserPort;
import com.cleanarchitecture.application.port.out.SaveUserPort;
import com.cleanarchitecture.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements SaveUserPort, LoadUserPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public Optional<User> loadUserByUsername(String username) {
        Optional<MongoUser> optionalUser = userRepository.findByUsername(username);
        return optionalUser.map(userMapper::toUser);
    }

    @Override
    public Boolean existUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User save(User user) {
        MongoUser mongoUser = userMapper.toMongoUser(user);
        userRepository.save(mongoUser);
        return userMapper.toUser(mongoUser);
    }
}
