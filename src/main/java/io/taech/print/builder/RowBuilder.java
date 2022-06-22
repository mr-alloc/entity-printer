package io.taech.print.builder;

public interface RowBuilder {

    RowBuilder proceed(Object target, Class<?> type);

    String build();
}
