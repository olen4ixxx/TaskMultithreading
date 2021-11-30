package io.olen4ixxx.ship.main;

import io.olen4ixxx.ship.entity.Ship;
import io.olen4ixxx.ship.entity.ShipLoadType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainThread {
    private static final Logger logger = LogManager.getLogger();
    public static final int SHIPS_NUMBER = 4; // FIXME: 28.11.2021
    public static ShipLoadType type;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(SHIPS_NUMBER);
        for (int i = 0; i < SHIPS_NUMBER; i++) {
            if (i < SHIPS_NUMBER / 2) type = ShipLoadType.LOAD;
            else type = ShipLoadType.UNLOAD;
//            new Ship(i, SHIPS_NUMBER, type).start();
            service.execute(new Ship(i, SHIPS_NUMBER, type));
        }
    }
}
