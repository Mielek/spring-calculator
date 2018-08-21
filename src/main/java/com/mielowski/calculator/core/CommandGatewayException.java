package com.mielowski.calculator.core;

public class CommandGatewayException extends RuntimeException {
    private Command command;

    CommandGatewayException(String message, Command command) {
        super(message);
        this.command = command;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " ["+command.toString()+"]";
    }
}
