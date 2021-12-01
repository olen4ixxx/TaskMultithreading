package io.olen4ixxx.ship.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static final Logger logger = LogManager.getLogger();
    private AtomicInteger numberOfContainers = new AtomicInteger(10);
    public static final int PORT_CONTAINER_CAPACITY = 20;
    private static final int NUMBER_OF_PIERS = 3;

    private Lock lock = new ReentrantLock();
    private final Deque<Condition> waitingThreadConditions = new ArrayDeque<>();
    private final Deque<Pier> freePiers;

    private static class SingletonHolder {
        public static final Port HOLDER_INSTANCE = new Port();
    }

    public static Port getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    private Port() { // TODO: 29.11.2021
        freePiers = new ArrayDeque<>();
        for (int i = 0; i < NUMBER_OF_PIERS; i++) {
            freePiers.add(new Pier());
        }
    }

    public Pier takePier() {
        Pier pier;
        if (freePiers.isEmpty()) {
            lock.lock();
            Condition condition = lock.newCondition();
            try {
                waitingThreadConditions.add(condition);
                condition.await();
            } catch (InterruptedException e) {
                logger.error("Pier take failed", e);
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
        pier = freePiers.pop();
        logger.info("Pier №{} is taken", pier.getPierId());
        return pier;
    }

    public void releasePier(Pier pier) {
        lock.lock();
        try {
            if (freePiers.size() <= NUMBER_OF_PIERS) {
                freePiers.push(pier);
                Condition condition = waitingThreadConditions.poll();
                if (condition != null) {
                    condition.signal();
                }
            }
        } finally {
            logger.info("Pier №{} is released", pier.getPierId());
            lock.unlock();
        }
    }

    public int getNumberOfContainers() {
        return numberOfContainers.intValue();
    }

    public int changeNumberOfContainers(int number) {
        return  numberOfContainers.addAndGet(number);
    }
}
