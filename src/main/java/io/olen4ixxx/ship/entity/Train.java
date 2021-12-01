package io.olen4ixxx.ship.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Train {
    private static final Logger logger = LogManager.getLogger();
    private static final int RELOAD_AMOUNT = Port.PORT_CONTAINER_CAPACITY / 2;

    public void bringContainers() {
        Port port = Port.getInstance();
        logger.info("Train brings {} containers", RELOAD_AMOUNT);
        port.changeNumberOfContainers(RELOAD_AMOUNT);
    }

    public void takeAwayContainers() {
        Port port = Port.getInstance();
        logger.info("Train takes away {} containers", RELOAD_AMOUNT);
        port.changeNumberOfContainers(-RELOAD_AMOUNT);
    }
}
