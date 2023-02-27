package io.taech.print;

import io.taech.constant.BuilderType;
import io.taech.constant.PrintOption;
import io.taech.util.CommonUtils;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class PrintConfigurator<I> {

    private BuilderType builderType;
    private Set<PrintOption> options;
    private List<I> activateIndexes;
    private DateTimeFormatter dateTimeFormatter;

    private PrintConfigurator() {
        builderType(BuilderType.DEFAULT);
        this.options = new HashSet<>();
    }

    public static  <I> PrintConfigurator<I> create(BuilderType builderType) {
        PrintConfigurator<I> configurator = new PrintConfigurator<>();
        configurator.options = new HashSet<>();
        configurator.builderType = builderType;

        return configurator;
    }

    public BuilderType getBuilderType() {
        return this.builderType;
    }

    public Set<PrintOption> getOptions() {
        return this.options;
    }

    public List<I> getActivateIndexes() {
        return this.activateIndexes;
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return this.dateTimeFormatter;
    }


    //== Builder Methods ==//

    public PrintConfigurator builderType(final BuilderType builderType) {
        this.builderType = builderType;
        return this;
    }

    public PrintConfigurator excludeDataType() {
        this.options.add(PrintOption.NO_DATA_TYPE);
        return this;
    }

    public PrintConfigurator activateFields(final I... fieldIndexes) {
        this.activateIndexes = CommonUtils.isNull(fieldIndexes) ? new ArrayList<>() : Arrays.stream(fieldIndexes)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        this.options.add(PrintOption.EXCEPT_COLUMN);
        return this;
    }


    public PrintConfigurator dateformat(final DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
        this.options.add(PrintOption.DATETIME_FORMAT);
        return this;
    }

    public PrintConfigurator allowMultiLine() {
        this.options.add(PrintOption.ALLOW_MULTILINE);
        return this;
    }
}
