package io.taech.print.builder;

import io.taech.print.PrintConfigurator;

public interface RowBuilder<I> {

    RowBuilder proceed(Object target, Class<?> type);

    RowBuilder config(PrintConfigurator<I> configurator);

    String build();
}
