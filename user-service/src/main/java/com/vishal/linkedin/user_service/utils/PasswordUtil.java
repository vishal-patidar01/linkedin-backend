package com.vishal.linkedin.user_service.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Hash a password for the first time
    public static  String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    // Check that a plain text password matched a previously hashed one
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
