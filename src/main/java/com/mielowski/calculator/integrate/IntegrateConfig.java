package com.mielowski.calculator.integrate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IntegrateConfig {
    @Bean
    public IntegrateHandler getEulerIntegralHandler(){
        return new IntegrateHandler();
    }
}
