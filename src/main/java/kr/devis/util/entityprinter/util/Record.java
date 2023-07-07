package kr.devis.util.entityprinter.util;

public class Record {
    private final Integer sequence;
    private final String message;
    private final String times;
    private final long snapshotMillis;

    public Record(final Integer sequence, final String message, final String times, long snapshotMillis) {
        this.sequence = sequence;
        this.message = message;
        this.times = times;
        this.snapshotMillis = snapshotMillis;
    }

    public Integer getSequence() {
        return sequence;
    }

    public String getMessage() {
        return message;
    }

    public String getTimes() {
        return times;
    }

    public long getSnapshotMillis() {
        return snapshotMillis;
    }
}
