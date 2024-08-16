package utils;

import java.security.SecureRandom;

public class Utils {
    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();

    //Method to create Confirmation code
    public static String generateRandomTransactionCode(int length){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i< length; i++){
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }
}
