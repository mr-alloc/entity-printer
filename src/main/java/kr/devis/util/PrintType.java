package kr.devis.util;

import java.util.function.Function;

public enum PrintType {
    TO_SECOND(time -> String.format("%4.3f sec", (time / 1000.f))),
    TO_MILLISECOND(time -> String.format("%d ms", time)),
    ;

    private final Function<Long, String> printFunction;

    PrintType(Function<Long, String> printFunction) {
        this.printFunction = printFunction;
    }

    public Function<Long, String> getPrintFunction() {
        return this.printFunction;
    }
}
