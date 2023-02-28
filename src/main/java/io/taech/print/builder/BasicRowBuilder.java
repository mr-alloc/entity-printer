package io.taech.print.builder;

import io.taech.print.Column;
import io.taech.print.field.manager.DefaultPrintableFieldManager;
import io.taech.print.field.manager.PrintableFieldManager;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class BasicRowBuilder extends AbstractRowBuilder<Integer> {


    private PrintableFieldManager<Integer, Field> fieldManager;
    private Supplier<Stream<Object>> streamSupplier;

    @Override
    public RowBuilder<Integer> proceed(final Object target, Class<?> typeClass) {
        this.fieldManager = new DefaultPrintableFieldManager(typeClass);
        this.streamSupplier = () ->
                (Collection.class.isAssignableFrom(target.getClass())) ? ((Collection) target).stream() : Stream.of(target);
        super.initialize();
        return this;
    }

    @Override
    void calculateColumnInfo() {
        Arrays.stream(this.fieldManager.getActivatedFields()).forEach(field -> {
            final String fieldName = field.getName();
            final String name = field.getType().getSimpleName();

            final Column newColumn = new Column(fieldName, name, super.optionAware.isNonDataType());
            columns.add(newColumn);
        });

        this.setFieldValues();
    }

    @Override
    PrintableFieldManager getCurrentFieldManager() {
        return this.fieldManager;
    }

    private void setFieldValues() {
        this.streamSupplier.get()
                .filter(row -> this.fieldManager.getTypeClass().equals(row.getClass())).forEach(row -> {
                    final Field [] fields = this.fieldManager.getActivatedFields();
                    final Map<String, String> columnMap = new LinkedHashMap<>();

                    IntStream.range(0, fields.length).forEach(idx -> {
                        try {
                            final Field field = fields[idx];

                            final Column column = columns.get(idx);
                            final String fieldName = field.getName();
                            final String strValue = getStringValue(field.get(row), column);

                            columnMap.put(fieldName, strValue);
                        } catch(Exception skipped) {
                            skipped.printStackTrace();
                        }
                    });
                    super.columnMapList.add(columnMap);
                });
    }

}
