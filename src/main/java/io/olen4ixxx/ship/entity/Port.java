package io.olen4ixxx.ship.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static final Logger logger = LogManager.getLogger();
    private static final int NUMBER_OF_CONTAINERS_DEFAULT = 10;
    private static final int NUMBER_OF_PIERS = 2;

    private Lock lock = new ReentrantLock();
    private final Semaphore semaphore = new Semaphore(2, true);

    private final Deque<Condition> waitingThreadConditions = new ArrayDeque<>();

    private final Deque<Pier> piers;

    private static class SingletonHolder {
        public static final Port HOLDER_INSTANCE = new Port();
    }

    public static Port getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    private Port() { // TODO: 29.11.2021
        piers = new ArrayDeque<>();
        for (int i = 0; i < NUMBER_OF_PIERS; i++) {
            piers.add(new Pier());
        }
    }

    public Pier takePier() {
        Pier pier;
        if (piers.isEmpty()) {
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
        pier = piers.pop();
        logger.info("Pier №{} is taken", pier.getPierId());
        return pier;
    }

    public void releasePier(Pier pier) {
        lock.lock();
        try {
            if (piers.size() <= NUMBER_OF_PIERS) {
                piers.push(pier);
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
}
