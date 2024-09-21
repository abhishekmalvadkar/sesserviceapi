package com.amalvadkar.ses.common.utils;


import java.util.UUID;

public class PasswordUtils {

    public static String generateTempPassword(){
       return UUID.randomUUID().toString();
    }

    private PasswordUtils(){
        throw new AssertionError("No com.amalvadkar.ses.common.utils.PasswordUtils instances for you!");
    }
}
