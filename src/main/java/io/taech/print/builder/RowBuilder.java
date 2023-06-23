package io.taech.print.builder;

import io.taech.print.PrintConfigurator;

public interface RowBuilder<I> {

    RowBuilder<I> proceed(Object target, Class<?> type);

    RowBuilder<I> config(PrintConfigurator<I> configurator);

    String build();
}
