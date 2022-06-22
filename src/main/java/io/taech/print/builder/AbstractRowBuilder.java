package io.taech.print.builder;

import io.taech.constant.PrintOption;
import io.taech.print.Column;
import io.taech.print.PrintOptionAware;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class AbstractRowBuilder implements RowBuilder {

    protected Class<?> targetClass;

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
        this.targetClass = typeClass;

        extractClassInfo(target);
        this.calculateColumnInfo();
    }

    @Override
    public void options(final PrintOption... options) {


    }

    /**
     * Check class of target with such as first element of stream
     * @param target
     */
    private void extractClassInfo(final Object target) {
        if(target instanceof Collection) {
            this.streamSupplier = () -> ((Collection) target).stream();
        }else
            this.streamSupplier = () -> Stream.of(target);
    }

    abstract void calculateColumnInfo();


}
