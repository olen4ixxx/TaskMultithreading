package io.olen4ixxx.ship.STUDY;

public class TalkThread implements Runnable{
    @Override
    public void run() {
//        for (int i = 0; i < 7; i++) {
//            System.out.println("Talk" + i);
//        }
        try {
            build();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void build() throws InterruptedException {
        int k = 0;
//        synchronized (System.out) {
            do {
                System.out.println("T");
                k++;
                Thread.sleep(100);
            } while (k <10);
//        }
    }
}
