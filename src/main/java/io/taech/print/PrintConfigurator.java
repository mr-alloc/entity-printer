package io.taech.print;

import io.taech.constant.BuilderType;
import io.taech.constant.PrintOption;
import io.taech.util.CommonUtils;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PrintConfigurator<I> {

    private BuilderType builderType;
    private List<PrintOption> options;
    private List<?> activateIndexes;
    private DateTimeFormatter dateTimeFormatter;

    public PrintConfigurator() {
        this.builderType = BuilderType.DEFAULT;
    }
    public PrintConfigurator(final BuilderType builderType) {
        this.builderType = builderType;
    }

    public BuilderType getBuilderType() {
        return this.builderType;
    }

    public List<PrintOption> getOptions() {
        return this.options;
    }

    public List<?> getActivateIndexes() {
        return this.activateIndexes;
    }

    public PrintConfigurator options(final PrintOption... options) {
        this.options = CommonUtils.isNull(options) ? new ArrayList<>() : Arrays.stream(options)
                .distinct().collect(Collectors.toList());

        return this;
    }

    public PrintConfigurator activateFields(final I... fieldIndexes) {
        this.activateIndexes = CommonUtils.isNull(fieldIndexes) ? new ArrayList<>() : Arrays.stream(fieldIndexes)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        return this;
    }


    public PrintConfigurator dateformat(final DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
        return this;
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return this.dateTimeFormatter;
    }
}
