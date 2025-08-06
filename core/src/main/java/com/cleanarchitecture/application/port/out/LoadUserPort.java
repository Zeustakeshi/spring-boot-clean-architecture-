/*
 *  LoadUserPort
 *  @author: minhhieuano
 *  @created 8/7/2025 2:38 AM
 * */

package com.cleanarchitecture.application.port.out;

import com.cleanarchitecture.domain.entity.User;

import java.util.Optional;

public interface LoadUserPort {
    Optional<User> loadUserByUsername(String username);

    Boolean existUserByUsername(String username);
}
