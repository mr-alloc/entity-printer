package kr.devis.entityprinter.util;

import kr.devis.entityprinter.print.builder.BasicRowBuilder;
import kr.devis.entityprinter.print.builder.RowBuilder;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StopWatch {

    private Long startTime;
    private Stack<Moment> moments;

    public StopWatch() {
        this.startTime = null;
        this.moments = new Stack<>();
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public void pause(final String message) {
        if(CommonUtils.isNull(this.startTime))
            throw new NullPointerException("Start time cannot be null. Please use stopWatch.start() first.");

        Long oldTime = moments.isEmpty()
                ? startTime
                : moments.peek().getMillis();

        final Long executionTime = (System.currentTimeMillis() - oldTime);
        this.moments.add(new Moment(executionTime, message));
    }

    public String getResult() {
        final RowBuilder builder = new BasicRowBuilder();
        final List<Record> records = IntStream.range(0, moments.size()).mapToObj((idx) -> {
            final Moment moment = moments.get(idx);
            return new Record(idx + 1, moment.getMessage(), String.format("%4.4f sec",(moment.getMillis() / 1000.f)));
        }).collect(Collectors.toList());

        return builder.proceed(records, Record.class)
                .build();
    }
}
