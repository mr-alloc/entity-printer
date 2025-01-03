package io.alloc.util;

public class Moment {
    private final String message;
    private final Long actualMillis;
    private final Long snapshotMillis;

    public Moment(final String message, final Long actualMillis, final Long snapshotMillis) {
        this.message = message;
        this.actualMillis = actualMillis;
        this.snapshotMillis = snapshotMillis;
    }

    public Long getMillis() {
        return this.actualMillis;
    }

    public Long getSnapshotMillis() {
        return this.snapshotMillis;
    }

    public String getMessage() {
        return this.message;
    }
}
