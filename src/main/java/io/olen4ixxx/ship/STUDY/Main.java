package io.olen4ixxx.ship.STUDY;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<String> future = service.submit(new CallThread());

//        Pattern p = Pattern.compile("(1*)0");
//        Matcher m = p.matcher("111110");
//        System.out.println(m.group(1));

//        StringBuilder sb2 = new StringBuilder("123");
//        char[] name = {'J', 'a', 'v', 'a'};
//        sb2.insert(1, name, 1, 3);
//        System.out.println(sb2);

        Integer i = new Integer(5);
        System.out.println(i.doubleValue());
        System.out.print(i);
    }
}
