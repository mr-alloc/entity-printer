package io.taech.print.builder;

import io.taech.constant.PrintOption;

import java.util.List;

public interface RowBuilder {

    RowBuilder proceed(Object target, Class<?> type);

    RowBuilder options(List<PrintOption> options);

    RowBuilder activateFields(List<Integer> indexes);


    String build();
}
