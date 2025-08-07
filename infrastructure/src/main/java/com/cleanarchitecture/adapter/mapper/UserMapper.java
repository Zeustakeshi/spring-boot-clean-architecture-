/*
 *  UserMapper
 *  @author: minhhieuano
 *  @created 8/7/2025 3:19 AM
 * */


package com.cleanarchitecture.adapter.mapper;

import com.cleanarchitecture.adapter.in.web.dto.request.CreateReaderRequest;
import com.cleanarchitecture.adapter.out.persistence.entity.MongoUser;
import com.cleanarchitecture.application.port.in.CreateReaderCommand;
import com.cleanarchitecture.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public CreateReaderCommand toCreateReaderCommand(CreateReaderRequest request) {
        return new CreateReaderCommand(request.getUsername(), request.getPassword());
    }

    public MongoUser toMongoUser(User user) {
        return MongoUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public User toUser(MongoUser mongoUser) {
        return new User(mongoUser.getId(), mongoUser.getUsername(), mongoUser.getPassword(), mongoUser.getRole());
    }

}
