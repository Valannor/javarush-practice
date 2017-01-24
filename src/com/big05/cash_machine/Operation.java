package com.big05.cash_machine;

public enum Operation
{
    INFO,
    DEPOSIT,
    WITHDRAW,
    EXIT,
    LOGIN;

    public static Operation getAllowableOperationByOrdinal(Integer i)
    {
        Operation operation = null;
        if (i > 4 || i < 0) throw new IllegalArgumentException();

        if (i == 0) throw new IllegalArgumentException();
        else if (i == 1) operation = Operation.INFO;
        else if (i == 2) operation = Operation.DEPOSIT;
        else if (i == 3) operation = Operation.WITHDRAW;
        else if (i == 4) operation = Operation.EXIT;

        return operation;
    }
}
