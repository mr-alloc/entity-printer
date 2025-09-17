package io.alloc.print.builder;

import io.alloc.print.Column;
import io.alloc.print.field.manager.DefaultPrintableFieldManager;
import io.alloc.print.field.manager.PrintableFieldManager;
import io.alloc.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class BasicRowBuilder extends AbstractRowBuilder<Field> {

    private PrintableFieldManager<Field> fieldManager;
    private Supplier<Stream<Object>> streamSupplier;

    @Override
    @SuppressWarnings("unchecked")
    public RowBuilder proceed(final Object target, Class<?> typeClass) {
        this.fieldManager = new DefaultPrintableFieldManager(typeClass);
        this.streamSupplier = () -> Collection.class.isAssignableFrom(target.getClass())
                ? ((Collection<Object>) target).stream() : Stream.of(target);
        super.initialize();
        return this;
    }

    @Override
    protected void calculateColumnInfo() {
        for (Field field : this.fieldManager.getActivatedFields()) {
            try {
                final String fieldName = field.getName();
                final String name = field.getType().getSimpleName();

                final Column newColumn = new Column(fieldName, name, super.optionAware.isNonDataType());
                columns.add(newColumn);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        this.setFieldValues();
    }

    @Override
    protected PrintableFieldManager<Field> getCurrentFieldManager() {
        return this.fieldManager;
    }

    private void setFieldValues() {
        this.streamSupplier.get()
                .filter(row -> this.fieldManager.getTypeClass().equals(row.getClass()))
                .forEach(row -> {
                    final Field [] fields = this.fieldManager.getActivatedFields();
                    final Map<String, String> columnMap = new LinkedHashMap<>();

                    IntStream.range(0, fields.length).forEach(idx -> {
                        try {
                            final Field field = fields[idx];
                            final String strValue = getStringValue(
                                    ReflectionUtils.getField(field, row), columns.get(idx));

                            columnMap.put(field.getName(), strValue);
                        } catch(Exception skipped) {
                            skipped.printStackTrace();
                        }
                    });
                    super.columnMapList.add(columnMap);
                });
    }

}
