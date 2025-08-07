/*
 *  UserRepository
 *  @author: minhhieuano
 *  @created 8/7/2025 3:23 AM
 * */


package com.cleanarchitecture.adapter.out.persistence.repository;

import com.cleanarchitecture.adapter.out.persistence.entity.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<MongoUser, String> {
    Optional<MongoUser> findByUsername(String username);

    Boolean existsByUsername(String username);
}
