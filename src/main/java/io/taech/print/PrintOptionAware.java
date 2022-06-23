package io.taech.print;

import io.taech.constant.PrintOption;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class PrintOptionAware {

    private boolean nonDataType = false;
    private boolean exceptColumn = false;
    private boolean koreanMode = false;

    private Set<PrintOption> optionSet;
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

    public PrintOptionAware(final PrintOption... options) {
        this.optionSet = Arrays.stream(options).collect(Collectors.toSet());
        activate();
    }

    public boolean isNonDataType() {
        return this.nonDataType;
    }

    public boolean isExceptColumn() {
        return this.exceptColumn;
    }

    public boolean isKoreanMode() {
        return this.koreanMode;
    }


    public void activate() {
        if(optionSet.isEmpty())
            return;

        optionSet.stream().forEach((o) -> checkOption(o));
    }

    public void checkOption(final PrintOption option) {
        switch (option) {
            case NO_DATA_TYPE:
                this.nonDataType = true;
                break;
            case EXCEPT_COLUMN:
                this.exceptColumn = true;
                break;
            case KOREAN_MODE:
                this.koreanMode = true;
                break;
            default:
                throw new IllegalArgumentException("Not found print option.");
        }
    }

    public void setDateFormatter(final DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public DateTimeFormatter getDateFormatter() {
        return this.formatter;
    }


}
