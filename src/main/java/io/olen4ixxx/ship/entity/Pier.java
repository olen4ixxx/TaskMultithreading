package io.olen4ixxx.ship.entity;

import io.olen4ixxx.ship.util.PierIdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Pier {
    private static final Logger logger = LogManager.getLogger();
    private final int pierId;
    private final Train train;
    private static final double RELOAD_LOW = Port.PORT_CONTAINER_CAPACITY * 0.2;
    private static final double RELOAD_HIGH = Port.PORT_CONTAINER_CAPACITY * 0.8;

    public int getPierId() {
        return pierId;
    }

    public Pier() {
        pierId = PierIdGenerator.generateId();
        train = new Train();
    }

    public void serviceShip(Ship ship) {
        Port port = Port.getInstance();
        ShipLoadType shipLoadType = ship.getShipLoadType();
        logger.info("Ship №{} {}", ship.getShipId(), shipLoadType);
        try {
            if (shipLoadType == ShipLoadType.LOAD) {
                while (ship.getNumberOfContainers() < Ship.SHIP_CONTAINER_CAPACITY) {
                    int shipContainers = ship.changeNumberOfContainers(1);
                    int portContainers = port.changeNumberOfContainers(-1);
                    logger.info("Ship №{} containers: {}, portContainers: {}",
                            ship.getShipId(), shipContainers, portContainers);
                    if (portContainers <= RELOAD_LOW) {
                        train.bringContainers();
                    }
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(200, 1000));
                }
            } else {
                while (ship.getNumberOfContainers() > 0) {
                    int shipContainers = ship.changeNumberOfContainers(-1);
                    int portContainers = port.changeNumberOfContainers(1);
                    logger.info("Ship №{} containers: {}, portContainers: {}",
                            ship.getShipId(), shipContainers, portContainers);
                    if (portContainers >= RELOAD_HIGH) {
                        train.takeAwayContainers();
                    }
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100, 500));
                }
            }
        } catch (InterruptedException e) {
            logger.error("Ship service failed", e);
        }
    }
}
