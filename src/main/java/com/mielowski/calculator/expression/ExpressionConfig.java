package com.mielowski.calculator.expression;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpressionConfig {

    @Bean
    public ExpressionHandler getExpressionHandler(){
        return new ExpressionHandler();
    }
}
