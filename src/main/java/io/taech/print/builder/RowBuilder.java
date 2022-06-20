package io.taech.print.builder;

public interface RowBuilder {

    RowBuilder proceed(Object target);

    String build();

    String getResult();
}
