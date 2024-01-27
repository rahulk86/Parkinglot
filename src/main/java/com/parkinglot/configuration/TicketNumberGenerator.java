package com.parkinglot.configuration;

import java.util.Random;

public class TicketNumberGenerator {
    private static final String ALPHANUMERIC_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String generateRandomTicketNumber(int length) {
        Random random = new Random();
        StringBuilder ticketNumber = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALPHANUMERIC_CHARS.length());
            char randomChar = ALPHANUMERIC_CHARS.charAt(randomIndex);
            ticketNumber.append(randomChar);
        }

        return ticketNumber.toString();
    }

}
