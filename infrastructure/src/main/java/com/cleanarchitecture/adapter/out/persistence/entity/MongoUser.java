/*
 *  User
 *  @author: minhhieuano
 *  @created 8/7/2025 3:22 AM
 * */


package com.cleanarchitecture.adapter.out.persistence.entity;

import com.cleanarchitecture.domain.valueObject.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MongoUser {
    private String id;
    private String username;
    private String password;
    private Role role;
}
