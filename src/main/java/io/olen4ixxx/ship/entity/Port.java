package io.olen4ixxx.ship.entity;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class Port {
    private final Pier[] piers;
    private final ContainerPlace[] portStorage;
//    private final AtomicReference<Pier>[] piers;
//    private final AtomicReference<ContainerPlace>[] portStorage;

    private static class SingletonHolder {
        public static final Port HOLDER_INSTANCE = new Port();
    }

    public static Port getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    private Port() { // TODO: 29.11.2021  
        int piersNumber = 2;
        int numberOfContainers = 10;
        int portStorageCapacity = 20;
        piers = new Pier[piersNumber];
        for (int i = 0; i < piersNumber; i++) {
            piers[i] = new Pier();
        }
        portStorage = new ContainerPlace[portStorageCapacity];
        for (int i = 0; i < numberOfContainers; i++) {
            portStorage[i] = new ContainerPlace(false);
        }
        for (int i = numberOfContainers; i < portStorageCapacity; i++) {
            portStorage[i] = new ContainerPlace(true);
        }
    }

    public Pier[] getPiers() {
        return piers;
    }

    public ContainerPlace[] getPortStorage() {
        return portStorage;
    }
}
