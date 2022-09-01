package io.taech.print.builder;

import io.taech.print.field.manager.DefaultPrintableFieldManager;
import io.taech.print.field.manager.PrintableFieldManager;
import io.taech.print.Column;
import io.taech.print.PrintConfigurator;
import io.taech.print.PrintOptionAware;
import io.taech.util.CommonUtils;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class AbstractRowBuilder implements RowBuilder {


    protected Supplier<Stream<Object>> streamSupplier;

    protected final StringBuilder builder = new StringBuilder();
    protected final List<Column> columns = new ArrayList<>();
    protected final List<Map<String, String>> columnMapList = new ArrayList<>();
    protected String floor;
    protected String room;

    protected final String EMPTY = "empty";
    protected final String NO_ACTIVATED = "There are no Activated fields";

    protected PrintOptionAware optionAware;
    private PrintConfigurator configurator;
    private Class<?> typeClass;

    protected void initialize(final Object target, Class<?> typeClass) {
        this.floor = null;
        this.room = null;
        this.streamSupplier = null;
        this.typeClass = typeClass;

        if (optionAware.isExceptColumn())
            this.getCurrentFieldManager().activatePrintableFields(configurator.getActivateIndexes());

        if(optionAware.hasDateTimeFormat())
            this.optionAware.setDateFormatter(configurator.getDateTimeFormatter());

        if (this.getCurrentFieldManager().getActivatedFields().length == 0)
            return;


        extractClassInfo(target);
        this.calculateColumnInfo();
    }

    @Override
    public RowBuilder config(final PrintConfigurator configurator) {
        if(CommonUtils.isNull(configurator))
            return this;

        this.optionAware = new PrintOptionAware(configurator.getOptions());
        this.configurator = configurator;

        return this;
    }

    /**
     * Check class of target with such as first element of stream
     * @param target
     */
    private void extractClassInfo(final Object target) {
        if(target instanceof Collection)
            this.streamSupplier = () -> ((Collection) target).stream();
        else if(target instanceof Map)
            this.streamSupplier = () -> ((Map) target).entrySet().stream();
        else
            this.streamSupplier = () -> Stream.of(target);
    }

    abstract void calculateColumnInfo();

    abstract PrintableFieldManager getCurrentFieldManager();

}
