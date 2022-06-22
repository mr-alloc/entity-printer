package io.taech.print;

import io.taech.constant.PrintOption;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class PrintOptionAware {

    private Boolean printDataType = false;
    private Boolean exceptColumn = false;
    private Boolean hasCustomDateTime = false;
    private Boolean koreanMode = false;

    private Set<PrintOption> optionSet;
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;


    public PrintOptionAware(final PrintOption... options) {
        this.optionSet = Arrays.stream(options).collect(Collectors.toSet());
        activate();
    }

    public void activate() {
        if(optionSet.isEmpty())
            return;


    }

    public DateTimeFormatter getDateFormatter() {
        return this.formatter;
    }


}
