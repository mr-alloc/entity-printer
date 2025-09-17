package io.alloc.print.builder;

import io.alloc.print.PrintConfigurator;

/**
 * Row를 만들기 위한 빌더 인터페이스
 */
public interface RowBuilder {

    RowBuilder proceed(Object target, Class<?> type);

    RowBuilder config(PrintConfigurator configurator);

    String build();
}
