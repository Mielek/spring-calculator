package com.mielowski.calculator;

import com.mielowski.calculator.core.CommandGateway;
import com.mielowski.calculator.core.CommandHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.ParameterizedType;

@SpringBootApplication
public class CalculatorApp {

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
