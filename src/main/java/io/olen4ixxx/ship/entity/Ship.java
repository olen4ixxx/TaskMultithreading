package io.olen4ixxx.ship.entity;

import io.olen4ixxx.ship.util.ShipIdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class Ship extends Thread {
    private static final Logger logger = LogManager.getLogger();
    private final int shipId;
    private final ShipLoadType shipLoadType;
    private final AtomicInteger numberOfContainers;
    public static final int SHIP_CONTAINER_CAPACITY = 6;

    public Ship(int numberOfContainers, ShipLoadType shipLoadType) {
        shipId = ShipIdGenerator.generateId();
        this.numberOfContainers = new AtomicInteger(numberOfContainers);
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

    public int getNumberOfContainers() {
        return numberOfContainers.intValue();
    }

    public int changeNumberOfContainers(int number) {
        return numberOfContainers.addAndGet(number);
    }
}
