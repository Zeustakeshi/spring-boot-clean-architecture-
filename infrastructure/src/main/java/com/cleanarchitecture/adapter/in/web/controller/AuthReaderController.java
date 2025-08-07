/*
 *  AuthReaderController
 *  @author: minhhieuano
 *  @created 8/7/2025 3:10 AM
 * */


package com.cleanarchitecture.adapter.in.web.controller;

import com.cleanarchitecture.adapter.in.web.dto.request.CreateReaderRequest;
import com.cleanarchitecture.adapter.mapper.UserMapper;
import com.cleanarchitecture.application.port.in.CreateReaderCommand;
import com.cleanarchitecture.application.useCase.CreateReaderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/reader")
@RequiredArgsConstructor
public class AuthReaderController {

    private final CreateReaderUseCase createReaderUseCase;
    private final UserMapper userMapper;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello");
    }

    @PostMapping("/register")
    public ResponseEntity<?> createReader(
            @RequestBody CreateReaderRequest request
    ) {
        CreateReaderCommand command = userMapper.toCreateReaderCommand(request);
        return ResponseEntity.ok(createReaderUseCase.createUser(command));
    }
}
