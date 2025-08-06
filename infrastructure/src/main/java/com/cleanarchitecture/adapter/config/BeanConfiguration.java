/*
 *  BeanConfiguration
 *  @author: minhhieuano
 *  @created 8/7/2025 3:32 AM
 * */


package com.cleanarchitecture.adapter.config;

import com.cleanarchitecture.application.port.out.LoadUserPort;
import com.cleanarchitecture.application.port.out.SaveUserPort;
import com.cleanarchitecture.application.service.CreateReaderService;
import com.cleanarchitecture.application.useCase.CreateReaderUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CreateReaderUseCase createReaderUseCase(
            SaveUserPort saveUserPort,
            LoadUserPort loadUserPort
    ) {
        return new CreateReaderService(saveUserPort, loadUserPort);
    }
}
