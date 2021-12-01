package io.olen4ixxx.ship.entity;

import io.olen4ixxx.ship.util.PierIdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Pier {
    private static final Logger logger = LogManager.getLogger();
    private final int pierId;
    private final AtomicBoolean free;

    public boolean isFree() {
        return free.get();
    }

    public void setFree() {
        free.set(true);
    }

    public void setBusy() {
        free.set(false);
    }

    public int getPierId() {
        return pierId;
    }

    public Pier() {
        free = new AtomicBoolean(true);
        pierId = PierIdGenerator.generateId();
    }

    public void serviceShip(Ship ship) {
        logger.info("Ship №{} {}", ship.getShipId(), ship.getShipLoadType());
        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(3000,6000));
        } catch (InterruptedException e) {
            logger.error("Ship service failed", e);
        }
    }
}
