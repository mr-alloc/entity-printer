package io.taech.print.builder;

import java.util.Map;

public class MapRowBuilder extends AbstractRowBuilder{

    @Override
    public RowBuilder proceed(Object target, Class<?> type) {
        super.initialize(target, type);

        return this;
    }

    @Override
    void calculateColumnInfo() {

    }


    @Override
    public String build() {
        return null;
    }
}
