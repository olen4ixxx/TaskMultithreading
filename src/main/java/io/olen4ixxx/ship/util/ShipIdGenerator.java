package io.olen4ixxx.ship.util;

public class ShipIdGenerator {
    private static int counter = -1;

    private ShipIdGenerator() {
    }

    public static int generateId() {
        return ++counter;
    }
}
