package io.olen4ixxx.ship.parser;

public interface ShipParser {
    int getNumberOfPiers(String data);

    int getPortStorageCapacity(String data);

    int getPortNumberOfContainersDefault(String data);

    int getShipStorageCapacity(String data);

    int getShipNumberOfContainersDefault(String data);

    int getNumberOfShips(String data);
}
