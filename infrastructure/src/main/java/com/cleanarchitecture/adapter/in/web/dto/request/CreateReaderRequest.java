/*
 *  CreateReaderRequest
 *  @author: minhhieuano
 *  @created 8/7/2025 3:18 AM
 * */


package com.cleanarchitecture.adapter.in.web.dto.request;

import lombok.Data;

@Data
public class CreateReaderRequest {
    private String username;
    private String password;
}
