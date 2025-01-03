package io.alloc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StopWatch {

    private long startTime = 0;
    private final List<Moment> moments = new ArrayList<>();

    public static StopWatch startAndGet() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        return stopWatch;
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public void pause(final String message) {
        if (this.startTime == 0)
            throw new NullPointerException("Start time cannot be null. Please use stopWatch.start() first.");

        long oldTime = moments.isEmpty() ? startTime : moments.get(moments.size() - 1).getSnapshotMillis();
        long snapshotMillis = System.currentTimeMillis();
        long executionTime = (snapshotMillis - oldTime);

        this.moments.add(new Moment(message, executionTime, snapshotMillis));
    }

    public List<Record> getRecords(PrintType printType) {
        return IntStream.range(0, moments.size()).mapToObj(idx ->
                new Record(
                        idx + 1,
                        moments.get(idx).getMessage(),
                        printType.getPrintFunction().apply(moments.get(idx).getMillis()),
                        moments.get(idx).getSnapshotMillis()
                )
        ).collect(Collectors.toList());
    }
}
