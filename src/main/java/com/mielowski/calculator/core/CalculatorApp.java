package com.mielowski.calculator.core;

import com.mielowski.calculator.expression.ExpressionParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.ParameterizedType;

@SpringBootApplication
public class CalculatorApp {

    @Bean
    public Calculator getCalculator(){
        return expression -> new ExpressionParser(expression).parse();
    }

    @Bean
    public CommandGateway getCommandGateway(ApplicationContext applicationContext){
        CommandGateway gateway = new CommandGateway();
        applicationContext.getBeansOfType(CommandHandler.class).forEach((s, handler) -> {
            Class cmdClass = (Class) ((ParameterizedType) handler.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
            gateway.registerHandler(cmdClass, handler);
        });
        return gateway;
    }

    public static void main(String[] args){
        SpringApplication.run(CalculatorApp.class, args);
    }
}
