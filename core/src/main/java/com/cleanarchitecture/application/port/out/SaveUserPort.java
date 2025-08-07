/*
 *  SaveUserPort
 *  @author: minhhieuano
 *  @created 8/7/2025 2:35 AM
 * */

package com.cleanarchitecture.application.port.out;

import com.cleanarchitecture.domain.entity.User;

public interface SaveUserPort {
    User save(User user);
}
