package kr.devis.util.entityprinter.util;

public class Moment {
    private Long milliTimes;
    private String message;

    public Moment(final Long milliTimes, final String message) {
        this.milliTimes = milliTimes;
        this.message = message;
    }

    public Long getMillis() {
        return this.milliTimes;
    }

    public String getMessage() {
        return this.message;
    }
}
