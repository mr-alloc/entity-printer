package io.taech.print.builder;

import io.taech.print.PrintConfigurator;
import io.taech.print.field.manager.PrintableFieldManager;
import io.taech.print.field.manager.PrintableMapManager;

import java.util.Map;

public class MappableRowBuilder extends AbstractRowBuilder {

    private PrintableFieldManager<String, Map.Entry> fieldManager;

    @Override
    public RowBuilder proceed(Object target, Class<?> type) {
        this.fieldManager = new PrintableMapManager(target);
        return null;
    }

    @Override
    public String build() {
        return null;
    }

    @Override
    void calculateColumnInfo() {

    }

    @Override
    PrintableFieldManager getCurrentFieldManager() {
        return null;
    }
}

