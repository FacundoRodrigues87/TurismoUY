package com.sc.utilidades;

public class log {
    @SuppressWarnings("unused")
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    @SuppressWarnings("unused")
    private static final String ANSI_PURPLE = "\u001B[35m";
    @SuppressWarnings("unused")
    private static final String ANSI_CYAN = "\u001B[36m";
    @SuppressWarnings("unused")
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_RESET = "\u001B[0m";

    public static void msg(String message) {
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }

    public static void info(String message) {
        System.out.println(ANSI_GREEN + "[INFO] " + message + ANSI_RESET);
    }

    public static void warning(String message) {
        System.out.println(ANSI_YELLOW + "[WARNING] " + message + ANSI_RESET);
    }

    public static void error(String message) {
        System.out.println(ANSI_RED + "[ERROR] " + message + ANSI_RESET);
    }
}
