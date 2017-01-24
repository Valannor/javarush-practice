package com.big05.cash_machine.command;

import com.big05.cash_machine.exception.InterruptOperationException;

interface Command
{
    void execute() throws InterruptOperationException;
}
