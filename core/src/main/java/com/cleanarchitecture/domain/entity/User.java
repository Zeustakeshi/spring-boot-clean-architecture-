/*
 *  User
 *  @author: minhhieuano
 *  @created 8/7/2025 2:18 AM
 * */


package com.cleanarchitecture.domain.entity;

import com.cleanarchitecture.domain.valueObject.Role;

public class User {
    private String id;
    private String username;
    private String password;
    private Role role;

    public User() {
    }

    public User(String id, String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
