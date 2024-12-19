package com.laptrinhoop.utils;

import java.security.SecureRandom;

public class CustomerUtils {
    /**
     * Generate password
     *
     * @param lengthNumber    number
     * @param LengthUppercase characters uppercase
     * @param LengthLowercase characters lowercase
     * @param lengthSpecial   characters special
     * @return string random password
     */
    public static String generateRandomPassword(int lengthNumber, int LengthUppercase, int LengthLowercase, int lengthSpecial) {

        final String number = "0123456789";
        final String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String lowercase = "abcdefghijklmnopqrstuvwxyz";
        final String special = "!@#$%^&*()";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        // number
        for (int i = 0; i < lengthNumber; i++) {
            int randomIndex = random.nextInt(number.length());
            sb.append(number.charAt(randomIndex));
        }

        // uppercase
        for (int i = 0; i < LengthUppercase; i++) {
            int randomIndex = random.nextInt(uppercase.length());
            sb.append(uppercase.charAt(randomIndex));
        }

        // lowercase
        for (int i = 0; i < LengthLowercase; i++) {
            int randomIndex = random.nextInt(lowercase.length());
            sb.append(lowercase.charAt(randomIndex));
        }

        // Special
        for (int i = 0; i < lengthSpecial; i++) {
            int randomIndex = random.nextInt(special.length());
            sb.append(special.charAt(randomIndex));
        }

        return sb.toString();
    }
}
