package com.mielowski.calculator.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandGateway {
    private Map<Class<? extends Command>, CommandHandler> handlersMap = new HashMap<>();

    public void registerHandler(Class<? extends Command> command, CommandHandler handler){
        handlersMap.put(command, handler);
    }

    public <ReturnT> ReturnT execute(Command command){
        CommandHandler<Command, ReturnT> commandHandler = retrieveHandler(command);
        return commandHandler.handle(command);
    }

    private <ReturnT> CommandHandler<Command,ReturnT> retrieveHandler(final Command command) {
        return Optional.ofNullable(handlersMap.get(command.getClass()))
                .orElseThrow(() -> new CommandGatewayException("No Handler for command", command));
    }

}
