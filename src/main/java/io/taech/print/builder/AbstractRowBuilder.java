package io.taech.print.builder;

import io.taech.print.Column;
import io.taech.util.CommonUtils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public abstract class AbstractRowBuilder implements RowBuilder {

    protected Class<?> targetClass;
    protected Stream<Object> rowStream;

    protected final StringBuilder builder = new StringBuilder();
    protected final List<Column> columns = new ArrayList<>();
    protected final List<Map<String, String>> columnMapList = new ArrayList<>();

    protected void initialize(final Object target) {
        final Class<?> clazz = extractClassInfo(target);

        if(CommonUtils.isNull(clazz))
            throw new IllegalArgumentException();


    }

    private Class<?> extractClassInfo(final Object target) {

        if(target instanceof Collection) {
            this.rowStream = ((Collection) target).stream();

        } else if(target instanceof Map) {
            this.rowStream = ((Map) target).entrySet().stream();

        } else {
            this.rowStream = Stream.of(target);
        }

        return null;
    }

    abstract void calculateColumnInfo();


}
