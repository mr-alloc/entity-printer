package io.taech.print.builder;

import io.taech.constant.PrintOption;

public interface RowBuilder {

    RowBuilder proceed(Object target, Class<?> type);

    void options(PrintOption... options);

    String build();
}
