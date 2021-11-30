package io.olen4ixxx.ship.entity;

import io.olen4ixxx.ship.util.ShipIdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ship extends Thread {
    private static final Logger logger = LogManager.getLogger();
    private final int shipId;
    private final ContainerPlace[] shipStorage;
    private final ShipLoadType shipLoadType;

    private final Semaphore semaphore;
    private final Pier[] piers;
    private final ContainerPlace[] portStorage;
    private final Lock unloadLock;
    private final Lock loadLock;
    private final Lock takeLock;
    private final Lock releaseLock;

    public Ship(int numberOfContainers, int shipStorageCapacity, ShipLoadType shipLoadType) {
        shipId = ShipIdGenerator.generateId();
        unloadLock = new  ReentrantLock();
        loadLock = new  ReentrantLock();
        takeLock = new  ReentrantLock();
        releaseLock = new  ReentrantLock();
        Port port = Port.getInstance();
        semaphore = new Semaphore(port.getPiers().length, true);
        piers = port.getPiers();
        portStorage = port.getPortStorage();
        this.shipLoadType = shipLoadType;
        shipStorage = new ContainerPlace[shipStorageCapacity];
        for (int i = 0; i < numberOfContainers; i++) {
            shipStorage[i] = new ContainerPlace(false);
        }
        for (int i = numberOfContainers; i < shipStorageCapacity; i++) {
            shipStorage[i] = new ContainerPlace(true);
        }
    }

    @Override
    public void run() {
        logger.info("Ship №{} is at port gate.", shipId);
        switch (shipLoadType) {
            case LOAD -> load();
            case UNLOAD -> unload();
        }
    }

    private void unload() {
        unloadLock.lock();
        try {
            int pierNumber = takePier();
            logger.info("Ship №{} unloads", shipId);
            for (int i = 0; i < shipStorage.length; i++) {
                if (!shipStorage[i].isFree()) {
                    shipStorage[i].setFree();
                    TimeUnit.SECONDS.sleep(1);
                    int j = 0;
                    while (j < portStorage.length) {
                        if (portStorage[j].isFree()) {
                            portStorage[j].setBusy();
                            logger.info("Ship №{} unloads container{} (Ship containers:{} Port containers:{})",
                                    shipId, i, getNumberOfContainersShip(), getNumberOfContainersPort());
                            break;
                        }
                        j++;
                    }
                    TimeUnit.SECONDS.sleep(1);
                }
            }
            releasePier(pierNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            unloadLock.unlock();
        }
    }

    private void load() {
        loadLock.lock();
        try {
            int pierNumber = takePier();
            logger.info("Ship №{} loads", shipId);
            for (int i = 0; i < portStorage.length; i++) {
                if (!portStorage[i].isFree()) {
                    portStorage[i].setFree();
                    TimeUnit.SECONDS.sleep(1);
                    int j = 0;
                    while (j < shipStorage.length) {
                        if (shipStorage[j].isFree()) {
                            shipStorage[j].setBusy();
                            logger.info("Ship №{} loads container{} (Ship containers:{} Port containers:{})",
                                    shipId, i, getNumberOfContainersShip(), getNumberOfContainersPort());
                            break;
                        }
                        j++;
                    }
                    TimeUnit.SECONDS.sleep(1);
                }
            }
            releasePier(pierNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            loadLock.unlock();
        }
    }

    private int takePier() throws InterruptedException {
        //acquire() запрашивает доступ к следующему за вызовом этого метода блоку кода,
        //если доступ не разрешен, поток вызвавший этот метод блокируется до тех пор,
        //пока семафор не разрешит доступ
        semaphore.acquire();
        int pierNumber = 0; // FIXME: 29.11.2021
        //Ищем свободное место и паркуемся
        takeLock.lock();
        try {
            int i = 0;
            while (i < piers.length) {
                if (piers[i].isFree()) {      //Если место свободно
                    piers[i].setBusy();  //занимаем его
                    pierNumber = i;         //Наличие свободного места гарантирует семафор
                    logger.info("Ship №{} is at pier №{}.", shipId, piers[i].getPierId()); // TODO: 28.11.2021
                    break;
                }
                i++;
            }
        } finally {
            takeLock.unlock();
        }
        TimeUnit.SECONDS.sleep(1);
        return pierNumber;
    }

    private void releasePier(int pierNumber) {
        releaseLock.lock();
        try {
            logger.info("Ship №{} leaves pier №{}.", shipId, piers[pierNumber].getPierId());
            piers[pierNumber].setFree();//Освобождаем место
        } finally {
            releaseLock.unlock();
        }
        //release(), напротив, освобождает ресурс
        semaphore.release();
    }

    private int getNumberOfContainersShip() {
        int number = 0;
        for (ContainerPlace containerPlace : shipStorage) {
            if (!containerPlace.isFree()) {
                number++;
            }
        }
        return number;
    }

    private int getNumberOfContainersPort() {
        int number = 0;
        for (ContainerPlace containerPlace : portStorage) {
            if (!containerPlace.isFree()) {
                number++;
            }
        }
        return number;
    }
}
