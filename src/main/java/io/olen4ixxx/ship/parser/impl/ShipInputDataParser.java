package io.olen4ixxx.ship.parser.impl;

import io.olen4ixxx.ship.parser.ShipParser;

import java.util.Arrays;

public class ShipInputDataParser implements ShipParser {
    private static final String NUMBER_REGEX = "[0-9]+";

    @Override
    public int getNumberOfPiers(String data) {
        String str = Arrays.stream(data.split("[\s]"))
                .filter(s -> s.matches(NUMBER_REGEX))
                .toList().get(0);
        return Integer.parseInt(str);
    }

    @Override
    public int getPortStorageCapacity(String data) {
        String str = Arrays.stream(data.split("\s"))
                .filter(s -> s.matches(NUMBER_REGEX))
                .toList().get(1);
        return Integer.parseInt(str);
    }

    @Override
    public int getPortNumberOfContainersDefault(String data) {
        String str = Arrays.stream(data.split("\s"))
                .filter(s -> s.matches(NUMBER_REGEX))
                .toList().get(2);
        return Integer.parseInt(str);
    }

    @Override
    public int getShipStorageCapacity(String data) {
        String str = Arrays.stream(data.split("\s"))
                .filter(s -> s.matches(NUMBER_REGEX))
                .toList().get(3);
        return Integer.parseInt(str);
    }

    @Override
    public int getShipNumberOfContainersDefault(String data) {
        String str = Arrays.stream(data.split("\s"))
                .filter(s -> s.matches(NUMBER_REGEX))
                .toList().get(4);
        return Integer.parseInt(str);
    }

    @Override
    public int getNumberOfShips(String data) {
        String str = Arrays.stream(data.split("\s"))
                .filter(s -> s.matches(NUMBER_REGEX))
                .toList().get(5);
        return Integer.parseInt(str);
    }
}
