package io.olen4ixxx.ship.entity;

import io.olen4ixxx.ship.util.ShipIdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ship extends Thread {
    private static final Logger logger = LogManager.getLogger();
    private final int shipId;
    private final ShipLoadType shipLoadType;
    private int numberOfContainers;
    private final int shipStorageCapacity;

    public Ship(int numberOfContainers, int shipStorageCapacity, ShipLoadType shipLoadType) {
        shipId = ShipIdGenerator.generateId();
        this.numberOfContainers = numberOfContainers;
        this.shipStorageCapacity = shipStorageCapacity;
        this.shipLoadType = shipLoadType;
    }

    @Override
    public void run() {
        logger.info("Ship №{} is at port gate.", shipId);
        Port port = Port.getInstance();
        Pier pier = port.takePier();
        pier.serviceShip(this);
        port.releasePier(pier);
    }

    public int getShipId() {
        return shipId;
    }

    public ShipLoadType getShipLoadType() {
        return shipLoadType;
    }
}
