package io.taech.util;

public class Record {
    private Integer sequence;
    private String times;
    private String message;

    public Record(final Integer sequence, final String message, final String times) {
        this.sequence = sequence;
        this.message = message;
        this.times = times;
    }
}
