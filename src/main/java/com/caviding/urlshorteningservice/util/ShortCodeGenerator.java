package com.caviding.urlshorteningservice.util;

import java.security.SecureRandom;

public class ShortCodeGenerator {

    private static final String BASE62 =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final int LENGTH = 9;

    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate() {
        StringBuilder shortCode = new StringBuilder(LENGTH);

        for (int i = 0; i < LENGTH; i++) {
            int index = RANDOM.nextInt(BASE62.length());
            shortCode.append(BASE62.charAt(index));
        }

        return shortCode.toString();
    }
}
