package io.alloc.print.builder;

import io.alloc.print.Column;
import io.alloc.print.field.manager.PrintableFieldManager;
import io.alloc.print.field.manager.PrintableMapManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MappableRowBuilder extends AbstractRowBuilder<String> {

    private PrintableFieldManager<String, Map.Entry<String, Object>> fieldManager;
    private Supplier<Stream<Map<String, Object>>> streamSupplier;

    @Override
    public RowBuilder<String> proceed(Object target, Class<?> typeClass) {
        this.fieldManager = new PrintableMapManager<>(typeClass, getKeyModelWithInitStream(target, typeClass));
        super.initialize();
        return this;
    }


    @Override
    protected void calculateColumnInfo() {
        Arrays.stream(this.fieldManager.getActivatedFields()).forEach(entry -> {
            try {

                String fieldName = entry.getKey();
                String name = entry.getValue().getClass().getSimpleName();

                final Column newColumn = new Column(fieldName, name, super.optionAware.isNonDataType());
                super.columns.add(newColumn);
            } catch (ClassCastException ex) {
                throw new IllegalArgumentException("The Key of Map.class must be String.class.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        this.setFieldValues();
    }

    @Override
    protected PrintableFieldManager<String, Map.Entry<String, Object>> getCurrentFieldManager() {
        return this.fieldManager;
    }

    private void setFieldValues() {
        this.streamSupplier.get().forEach(row -> {
            Map.Entry<String, Object>[] fields = this.fieldManager.getActivatedFields();
            Map<String, String> columnMap = new LinkedHashMap<>();

            IntStream.range(0, fields.length).forEach(idx -> {
                final Object field = row.get(fields[idx].getKey());

                final Column column = super.columns.get(idx);
                final String strValue = getStringValue(field, column);

                columnMap.put(column.getName(), strValue);
            });

            super.columnMapList.add(columnMap);
        });
    }


    private Map<String, Object> getKeyModelWithInitStream(Object target, Class<?> typeClass) {
        Map<String, Object> keyModel;

        if (target instanceof Collection && !typeClass.isAssignableFrom(Map.class)) {
            throw new IllegalArgumentException("target's inner type is not Map.class.");
        }

        if (Collection.class.isAssignableFrom(target.getClass())) {
            Collection<Map<String, Object>> mapList = (Collection<Map<String, Object>>) target;
            keyModel = mapList.stream().findFirst().orElseThrow(() ->
                    new IllegalArgumentException("Map object must be has element at least one."));

            this.streamSupplier = mapList::stream;

        } else if (Map.class.isAssignableFrom(target.getClass())) {
            keyModel = (Map<String, Object>) target;

            this.streamSupplier = () -> Stream.of(keyModel);
        } else {
            throw new IllegalArgumentException("Mappable row builder need Object of Map.class.");
        }

        return keyModel;
    }
}

