package kr.devis.entityprinter.print;

import kr.devis.entityprinter.constant.BuilderType;
import kr.devis.entityprinter.constant.PrintOption;
import kr.devis.entityprinter.util.CommonUtils;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class PrintConfigurator<I> {

    private final Set<PrintOption> options;
    private BuilderType builderType;
    private List<I> activateIndexes;
    private DateTimeFormatter dateTimeFormatter;

    private PrintConfigurator() {
        this.builderType = BuilderType.DEFAULT;
        this.options = new HashSet<>();
    }

    private PrintConfigurator(BuilderType builderType) {
        this.builderType = builderType;
        this.options = new HashSet<>();
    }

    public static <I> PrintConfigurator<I> create(BuilderType builderType) {
        return new PrintConfigurator<>(builderType);
    }

    public static <I> PrintConfigurator<I> create() {
        return new PrintConfigurator<>(BuilderType.DEFAULT);
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

    public PrintConfigurator<I> builderType(final BuilderType builderType) {
        this.builderType = builderType;
        return this;
    }

    public PrintConfigurator<I> excludeDataType() {
        this.options.add(PrintOption.NO_DATA_TYPE);
        return this;
    }

    public PrintConfigurator<I> activateFields(final I... fieldIndexes) {
        this.activateIndexes = CommonUtils.isNull(fieldIndexes) ? new ArrayList<>() : Arrays.stream(fieldIndexes)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        this.options.add(PrintOption.EXCEPT_COLUMN);
        return this;
    }


    public PrintConfigurator<I> dateformat(final DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
        this.options.add(PrintOption.DATETIME_FORMAT);
        return this;
    }

    public PrintConfigurator<I> allowMultiLine() {
        this.options.add(PrintOption.ALLOW_MULTILINE);
        return this;
    }

    public PrintConfigurator<I> withoutFloor() {
        this.options.add(PrintOption.WITHOUT_FLOOR);
        return this;
    }
}
