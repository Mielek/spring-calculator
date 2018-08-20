package com.mielowski.calculator.core;

public interface CommandHandler<CommandT extends Command, ReturnT> {
    ReturnT handle(CommandT command);
}
