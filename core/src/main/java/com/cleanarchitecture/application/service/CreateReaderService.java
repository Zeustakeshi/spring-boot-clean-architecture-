/*
 *  CreateUserService
 *  @author: minhhieuano
 *  @created 8/7/2025 2:37 AM
 * */


package com.cleanarchitecture.application.service;

import com.cleanarchitecture.application.port.in.CreateReaderCommand;
import com.cleanarchitecture.application.port.out.LoadUserPort;
import com.cleanarchitecture.application.port.out.SaveUserPort;
import com.cleanarchitecture.application.useCase.CreateReaderUseCase;
import com.cleanarchitecture.domain.entity.User;
import com.cleanarchitecture.domain.exception.UserAlreadyExistedException;
import com.cleanarchitecture.domain.valueObject.Role;

import java.util.UUID;

public class CreateReaderService implements CreateReaderUseCase {

    private final SaveUserPort saveUserPort;
    private final LoadUserPort loadUserPort;

    public CreateReaderService(
            SaveUserPort saveUserPort,
            LoadUserPort loadUserPort
    ) {
        this.saveUserPort = saveUserPort;
        this.loadUserPort = loadUserPort;
    }

    @Override
    public User createUser(CreateReaderCommand command) {
        if (loadUserPort.existUserByUsername(command.getUsername())) {
            throw new UserAlreadyExistedException();
        }

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(command.getUsername());
        user.setPassword(command.getPassword()); // this only use for test
        user.setRole(Role.READER);

        return saveUserPort.save(user);
    }
}
