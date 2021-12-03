package io.olen4ixxx.ship.util;

public class PierIdGenerator {
    private static int counter = -1;

    private PierIdGenerator() {
    }

    public static int generateId() {
        return ++counter;
    }
}
