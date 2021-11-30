package io.olen4ixxx.ship.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShipIdGenerator { // TODO: singletone
    private static final Logger logger = LogManager.getLogger();
    private static int counter = -1;

    private ShipIdGenerator() {
    }

    public static int generateId() {
//        logger.info("Id generated: {}", ++counter);

        return ++counter;
    }
}