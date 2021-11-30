package io.olen4ixxx.ship.STUDY;

import java.util.concurrent.Callable;

public class CallThread implements Callable<String> {

    @Override
    public String call() throws Exception {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            System.out.println("Call" + i);
            builder.append("Call" + i);
        }
        return builder.toString();
    }
}
