package io.taech.print.builder;

import io.taech.constant.PrintOption;

public interface RowBuilder {

    RowBuilder proceed(Object target, Class<?> type);

    RowBuilder options(PrintOption... options);

    RowBuilder activateFields(Integer... indexes);


    String build();
}
