package com.parkinglot.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketConfig {
    @Bean
    public ModelMapper ticketConfigModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper;
    }
}
