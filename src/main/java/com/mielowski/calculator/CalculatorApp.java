package com.mielowski.calculator;

import com.mielowski.calculator.expressions.ExpressionParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CalculatorApp {

    @Bean
    public Calculator getCalculator(){
        return expression -> new ExpressionParser(expression).parse();
    }

    public static void main(String[] args){
        SpringApplication.run(CalculatorApp.class, args);
    }
}
