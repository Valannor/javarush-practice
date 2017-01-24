package com.big10.shortener;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Helper
{
    private static SecureRandom secureRandom = new SecureRandom();

    public static String generateRandomString()
    {
        return new BigInteger(130, secureRandom).toString(32);
    }

    public static void printMessage(String message)
    {
        System.out.println(message);
    }
}
