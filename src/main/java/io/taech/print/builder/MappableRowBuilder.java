package io.taech.print.builder;

import io.taech.print.Column;
import io.taech.print.field.manager.PrintableFieldManager;
import io.taech.print.field.manager.PrintableMapManager;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MappableRowBuilder extends AbstractRowBuilder<String> {

    private PrintableFieldManager<String, Map.Entry<String, Object>> fieldManager;
    private Supplier<Stream<Map<String, Object>>> streamSupplier;

    @Override
    public RowBuilder<String> proceed(Object target, Class<?> typeClass) {
        this.fieldManager = new PrintableMapManager<>(typeClass, getKeyModelWithInitStream(target));
        super.initialize();
        return this;
    }


    @Override
    void calculateColumnInfo() {
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
    PrintableFieldManager getCurrentFieldManager() {
        return this.fieldManager;
    }

    private void setFieldValues() {
        this.streamSupplier.get().forEach(row -> {
            Map.Entry<String, Object>[] fields = this.fieldManager.getActivatedFields();
            Map<String, String> columnMap = new LinkedHashMap<>();

            IntStream.range(0, fields.length).forEach(idx -> {
                final Object field = row.get(fields[idx].getKey());

                final Column column = super.columns.get(idx);
                final String fieldName = column.getName();
                final String strValue = getStringValue(field, column);

                columnMap.put(fieldName, strValue);
            });
            super.columnMapList.add(columnMap);
        });
    }



    private Map<String, Object> getKeyModelWithInitStream(Object target) {
        Map<String, Object> keyModel;

        if(List.class.isAssignableFrom(target.getClass())) {
            List<Map<String, Object>> mapList = (List<Map<String, Object>>) target;
            keyModel = mapList.stream().findFirst().orElseThrow(() ->
                    new IllegalArgumentException("Map object must be has element at least one."));

            this.streamSupplier = () -> mapList.stream();

        } else if(Map.class.isAssignableFrom(target.getClass())) {
            keyModel = (Map<String, Object>) target;

            this.streamSupplier = () -> Stream.of(keyModel);
        } else {
            throw new IllegalArgumentException("Mappable row builder need Object of Map.class.");
        }

        return keyModel;
    }
}

