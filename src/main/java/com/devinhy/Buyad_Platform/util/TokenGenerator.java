package com.devinhy.Buyad_Platform.util;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {
    private static final SecureRandom random = new SecureRandom();

    public static String generateToken() {
        byte[] token = new byte[32];
        random.nextBytes(token);
        return Base64.getEncoder().withoutPadding().encodeToString(token);
    }
}
