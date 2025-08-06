/*
 *  Publisher
 *  @author: minhhieuano
 *  @created 8/7/2025 2:24 AM
 * */


package com.cleanarchitecture.domain.entity;

public class Publisher {
    private String id;
    private String name;

    public Publisher() {
    }

    public Publisher(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
