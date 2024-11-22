package com.example.model;

import java.util.EnumSet;

public enum Command {
    F, R, L, U;

    // Helper method to check if a character is present in the enum
    public static boolean contains(char c) {
        return EnumSet.allOf(Command.class).stream().anyMatch(e -> e.name().charAt(0) == Character.toUpperCase(c));
    }
}
