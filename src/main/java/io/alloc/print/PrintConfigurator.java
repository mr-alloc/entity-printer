package io.alloc.print;

import io.alloc.constant.BuilderType;
import io.alloc.constant.PrintOption;
import io.alloc.util.CommonUtils;

import java.time.format.DateTimeFormatter;
import java.util.*;

public final class PrintConfigurator {

    private final Set<PrintOption> options;
    private BuilderType builderType;
    private Set<String> activateNames;
    private DateTimeFormatter dateTimeFormatter;

    private PrintConfigurator(BuilderType builderType) {
        this.builderType = builderType;
        this.options = new HashSet<>();
    }

    public static <I> PrintConfigurator create(BuilderType builderType) {
        return new PrintConfigurator(builderType);
    }

    public static <I> PrintConfigurator create() {
        return new PrintConfigurator(BuilderType.ROW);
    }

    public BuilderType getBuilderType() {
        return this.builderType;
    }

    public Set<PrintOption> getOptions() {
        return this.options;
    }

    public Set<String> getActivatedNames() {
        return this.activateNames;
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return this.dateTimeFormatter;
    }

    public PrintConfigurator builderType(final BuilderType builderType) {
        this.builderType = builderType;
        return this;
    }

    public PrintConfigurator excludeDataType() {
        this.options.add(PrintOption.NO_DATA_TYPE);
        return this;
    }

    public PrintConfigurator activateFields(final String... fieldNames) {
        this.activateNames = CommonUtils.isNull(fieldNames) ? Collections.emptySet() : Set.of(fieldNames);
        this.options.add(PrintOption.EXCEPT_COLUMN);
        return this;
    }


    public PrintConfigurator dateformat(final String pattern) {
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        this.options.add(PrintOption.DATE_FORMAT);
        return this;
    }

    public PrintConfigurator allowMultiLine() {
        this.options.add(PrintOption.ALLOW_MULTILINE);
        return this;
    }

    public PrintConfigurator withoutFloor() {
        this.options.add(PrintOption.WITHOUT_EACH_BORDER_BOTTOM);
        return this;
    }

    public PrintConfigurator applyAll(PrintOption... printOptions) {
        this.options.addAll(Arrays.asList(printOptions));
        return this;
    }

    public PrintConfigurator noEscape() {
        this.options.add(PrintOption.NO_ESCAPE);
        return this;
    }

    public PrintConfigurator noEllipsis() {
        this.options.add(PrintOption.NO_ELLIPSIS);
        return this;
    }
}
