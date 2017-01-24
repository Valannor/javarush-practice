package com.big05.cash_machine.command;

import com.big05.cash_machine.Operation;
import com.big05.cash_machine.exception.InterruptOperationException;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor
{
    private CommandExecutor()
    {}

    private static Map<Operation, Command> commandMap;
    static
    {
        commandMap = new HashMap<>();
        commandMap.put(Operation.LOGIN, new LoginCommand());
        commandMap.put(Operation.INFO, new InfoCommand());
        commandMap.put(Operation.DEPOSIT, new DepositCommand());
        commandMap.put(Operation.WITHDRAW, new WithdrawCommand());
        commandMap.put(Operation.EXIT, new ExitCommand());
    }

    public static final void execute(Operation operation) throws InterruptOperationException
    {
        commandMap.get(operation).execute();
    }
}
