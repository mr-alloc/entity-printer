package io.taech.print.builder;

import io.taech.constant.PrintOption;
import io.taech.print.PrintConfigurator;

import java.util.List;

public interface RowBuilder {

    RowBuilder proceed(Object target, Class<?> type);

    RowBuilder config(PrintConfigurator configurator);

    String build();
}
