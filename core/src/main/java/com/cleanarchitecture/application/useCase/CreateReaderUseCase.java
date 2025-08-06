/*
 *  CreateUserUseCase
 *  @author: minhhieuano
 *  @created 8/7/2025 2:31 AM
 * */


package com.cleanarchitecture.application.useCase;

import com.cleanarchitecture.application.port.in.CreateReaderCommand;
import com.cleanarchitecture.domain.entity.User;

public interface CreateReaderUseCase {
    User createUser(CreateReaderCommand command);
}
