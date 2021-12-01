package io.olen4ixxx.ship.entityOLD;

import io.olen4ixxx.ship.util.ShipIdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
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
    private final Lock locking;
    private Deque<Condition> waitingThreadConditions = new ArrayDeque<>();

    public Ship(int numberOfContainers, int shipStorageCapacity, ShipLoadType shipLoadType) {
        shipId = ShipIdGenerator.generateId();
        locking = new  ReentrantLock();
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
        AtomicInteger pierNumber = new AtomicInteger(-1);
        try {
            semaphore.acquire();
            locking.lock();
//            if (pierNumber.intValue() < 0) {
//                Condition condition = locking.newCondition();
//                waitingThreadConditions.add(condition);
//                condition.await();
//            }
            pierNumber.set(takePier());
            locking.unlock();
//            service(pierNumber.intValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            locking.lock();
            releasePier(pierNumber.intValue());
            Condition condition = waitingThreadConditions.poll();
            if (condition != null) {
                condition.signal();
            }
        }
    }

    private void service(int pierNumber) throws InterruptedException {
//        TimeUnit.MILLISECONDS.sleep((int) (2000 * (1 + 2 * Math.random())));
        logger.info("Ship №{} {}", shipId, shipLoadType);
        switch (shipLoadType) {
            case LOAD -> piers[pierNumber].loadShip(portStorage, shipStorage);
            case UNLOAD -> piers[pierNumber].unloadShip(portStorage, shipStorage);
        }
    }

//    private void load() {
//        try {
//            //acquire() запрашивает доступ к следующему за вызовом этого метода блоку кода,
//            //если доступ не разрешен, поток вызвавший этот метод блокируется до тех пор,
//            //пока семафор не разрешит доступ
//            semaphore.acquire();
////            synchronized (piers) {
//                int pierNumber = takePier();
//                logger.info("Ship №{} loads", shipId);
//                TimeUnit.MILLISECONDS.sleep((int) (2000 * (1 + 2 * Math.random())));
//                synchronized (piers[pierNumber]) {
//                    piers[pierNumber].loadShip(portStorage, shipStorage);
//                }
//                releasePier(pierNumber);
////            }
//            //release(), напротив, освобождает ресурс
//            semaphore.release();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    private int takePier() throws InterruptedException {

        AtomicInteger pierNumber = new AtomicInteger(-1); // FIXME: 29.11.2021
            int i = 0;
            while (i < piers.length) {
                if (piers[i].isFree()) {      //Если место свободно
                    piers[i].setBusy();  //занимаем его
                    pierNumber.set(i);         //Наличие свободного места гарантирует семафор
                    logger.info("Ship №{} is at pier №{}.", shipId, piers[i].getPierId()); // TODO: 28.11.2021
                    break;
                }
                i++;
            }
        TimeUnit.SECONDS.sleep(1);
        return pierNumber.intValue();
    }

    private void releasePier(int pierNumber) {
        logger.info("Ship №{} leaves pier №{}.", shipId, piers[pierNumber].getPierId());
        piers[pierNumber].setFree();//Освобождаем место
        locking.unlock();
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
