package io.olen4ixxx.ship.main;

import io.olen4ixxx.ship.entity.Ship;
import io.olen4ixxx.ship.entity.ShipLoadType;
import io.olen4ixxx.ship.exception.ShipException;
import io.olen4ixxx.ship.parser.ShipParser;
import io.olen4ixxx.ship.parser.impl.ShipInputDataParser;
import io.olen4ixxx.ship.reader.ShipReader;
import io.olen4ixxx.ship.reader.impl.ShipFileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainThread {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        ShipReader reader = new ShipFileReader();
        ShipParser parser = new ShipInputDataParser();
        ShipLoadType type;
        try {
            String data = reader.readLines("files/data.txt");
            int numberOfShips = parser.getNumberOfShips(data);
            int numberOfContainers = parser.getShipNumberOfContainersDefault(data);
            ExecutorService service = Executors.newFixedThreadPool(numberOfShips);
            Random random = new Random();
            for (int i = 0; i < numberOfShips; i++) {
                if (random.nextBoolean()) {
                    type = ShipLoadType.LOAD;
                } else type = ShipLoadType.UNLOAD;
                service.submit(new Ship(numberOfContainers, type));
            }
            service.shutdown();
        } catch (ShipException e) {
            logger.error("File read failed", e);
        }

    }
}
