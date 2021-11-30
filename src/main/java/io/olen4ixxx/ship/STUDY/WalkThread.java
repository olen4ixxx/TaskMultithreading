package io.olen4ixxx.ship.STUDY;

import java.util.concurrent.TimeUnit;

public class WalkThread  extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 7; i++) {
            System.out.println("Walk" + i);
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
