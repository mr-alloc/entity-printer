package io.taech.print.builder;

import io.taech.DefaultPrintableFieldManager;
import io.taech.PrintableFieldManager;
import io.taech.constant.PrintOption;
import io.taech.print.Column;
import io.taech.print.PrintOptionAware;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class AbstractRowBuilder implements RowBuilder {


    protected PrintableFieldManager fieldManager;

    protected Supplier<Stream<Object>> streamSupplier;

    protected final StringBuilder builder = new StringBuilder();
    protected final List<Column> columns = new ArrayList<>();
    protected final List<Map<String, String>> columnMapList = new ArrayList<>();
    protected String floor;
    protected String room;

    protected PrintOptionAware optionAware;

    protected void initialize(final Object target, Class<?> typeClass) {
        this.floor = null;
        this.room = null;
        this.streamSupplier = null;
        this.fieldManager = new DefaultPrintableFieldManager(typeClass);

        extractClassInfo(target);
        this.calculateColumnInfo();
    }

    @Override
    public RowBuilder options(final List<PrintOption> options) {
        this.optionAware = new PrintOptionAware(options);
        return this;
    }

    @Override
    public RowBuilder activateFields(final List<Integer> indexes) {
        if(optionAware.isExceptColumn())
            this.fieldManager.activatePrintableFields(indexes);

        return this;
    }

    /**
     * Check class of target with such as first element of stream
     * @param target
     */
    private void extractClassInfo(final Object target) {
        if(target instanceof Collection)
            this.streamSupplier = () -> ((Collection) target).stream();
        else
            this.streamSupplier = () -> Stream.of(target);
    }

    abstract void calculateColumnInfo();


}
