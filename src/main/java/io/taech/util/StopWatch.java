package io.taech.util;

import io.taech.print.builder.BasicRowBuilder;
import io.taech.print.builder.RowBuilder;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class StopWatch {

    private Long startTime;
    private ArrayList<Long> times;

    public StopWatch() {
        this.startTime = null;
        this.times = new ArrayList<>();
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public void addAndPause() {
        this.times.add(System.currentTimeMillis());
    }

    public String getResult() {
        final ArrayList<Record> records = new ArrayList<>();
        IntStream.range(1, times.size()).forEach(idx -> {
            long time = times.get(idx) - times.get(idx - 1);
            records.add(new Record(idx, (time / 1000.0f)));
        });

        final RowBuilder builder = new BasicRowBuilder();
        return builder.proceed(records)
                .build();
    }
}
