package io.taech;

import io.taech.print.EntityPrinter;
import io.taech.util.StopWatch;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<History> list = new ArrayList<>();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        EntityPrinter out = new EntityPrinter();
        final String result = out.draw(list, History.class);
        System.out.println(result);
        System.out.println(stopWatch.getResult());

    }

}
