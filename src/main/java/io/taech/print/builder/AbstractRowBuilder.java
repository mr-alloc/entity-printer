package io.taech.print.builder;

import io.taech.print.Column;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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


    protected void initialize(final Object target) {
        this.floor = null;
        this.room = null;
        this.streamSupplier = null;

        extractClassInfo(target);
        this.calculateColumnInfo();
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

        this.targetClass = streamSupplier.get().findFirst().get().getClass();
    }

    abstract void calculateColumnInfo();


}
