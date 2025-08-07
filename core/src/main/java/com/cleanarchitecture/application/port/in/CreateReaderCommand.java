/*
 *  CreateUserCommand
 *  @author: minhhieuano
 *  @created 8/7/2025 2:33 AM
 * */


package com.cleanarchitecture.application.port.in;

public class CreateReaderCommand {
    private String username;
    private String password;

    public CreateReaderCommand() {
    }

    public CreateReaderCommand(String username, String password) {
        this.username = username;
        this.password = password;
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
