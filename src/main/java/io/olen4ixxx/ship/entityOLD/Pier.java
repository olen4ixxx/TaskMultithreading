package io.olen4ixxx.ship.entityOLD;

import io.olen4ixxx.ship.util.PierIdGenerator;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Pier {
    private final int pierId;
    private final AtomicBoolean free;

    public Pier() {
        free = new AtomicBoolean(true);
        pierId = PierIdGenerator.generateId();
    }

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

    public void unloadShip(ContainerPlace[] portStorage, ContainerPlace[] shipStorage) throws InterruptedException {
        for (int i = 0; i < shipStorage.length; i++) {
            if (!shipStorage[i].isFree()) {
                shipStorage[i].setFree();
                TimeUnit.SECONDS.sleep(1);
                int j = 0;
                while (j < portStorage.length) {
                    if (portStorage[j].isFree()) {
                        portStorage[j].setBusy();
//                            logger.info("Ship №{} unloads container{} (Ship containers:{} Port containers:{})",
//                                    shipId, i, getNumberOfContainersShip(), getNumberOfContainersPort());
                        break;
                    }
                    j++;
                }
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }

    public void loadShip(ContainerPlace[] portStorage, ContainerPlace[] shipStorage) throws InterruptedException {
        for (int i = 0; i < portStorage.length; i++) {
            if (!portStorage[i].isFree()) {
                portStorage[i].setFree();
                TimeUnit.SECONDS.sleep(1);
                int j = 0;
                while (j < shipStorage.length) {
                    if (shipStorage[j].isFree()) {
                        shipStorage[j].setBusy();
//                            logger.info("Ship №{} loads container{} (Ship containers:{} Port containers:{})",
//                                    shipId, i, getNumberOfContainersShip(), getNumberOfContainersPort());
                        break;
                    }
                    j++;
                }
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }
}
