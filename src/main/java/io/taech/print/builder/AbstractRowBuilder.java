package io.taech.print.builder;

import io.taech.print.Column;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class AbstractRowBuilder implements RowBuilder {

    protected Class<?> targetClass;

    protected Supplier<Stream<Object>> streamSupplier;
    private Stream<Object> rowStream;

    protected final StringBuilder builder = new StringBuilder();
    protected final List<Column> columns = new ArrayList<>();
    protected final List<Map<String, String>> columnMapList = new ArrayList<>();
    protected String floor;
    protected String room;


    protected void initialize(final Object target, Class<?> typeClass) {
        this.floor = null;
        this.room = null;
        this.streamSupplier = null;
        this.targetClass = typeClass;

        extractClassInfo(target);
        this.calculateColumnInfo();
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
