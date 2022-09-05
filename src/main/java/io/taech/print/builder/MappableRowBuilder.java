package io.taech.print.builder;

import io.taech.constant.Resource;
import io.taech.print.Column;
import io.taech.print.PrintConfigurator;
import io.taech.print.field.manager.PrintableFieldManager;
import io.taech.print.field.manager.PrintableMapManager;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static io.taech.constant.Resource.*;

public class MappableRowBuilder extends AbstractRowBuilder {

    private PrintableFieldManager<String, Map.Entry> fieldManager;
    private Supplier<Stream<Map<String, Object>>> streamSupplier;

    @Override
    public RowBuilder proceed(Object target, Class<?> typeClass) {

        Map<String, Object> keyModel = getKeyModelWithInitStream(target);

        this.fieldManager = new PrintableMapManager(typeClass, keyModel);
        super.initialize(keyModel, typeClass);
        this.setting();
        return this;
    }

    private void setting() {
        this.setFloor();
        this.setRoom();
    }


    @Override
    public String build() {
        final Supplier<Stream<Column>> columns = () -> super.columns.stream();
        if (this.fieldManager.getActivatedFields().length == 0)
            return builder.append(NO_ACTIVATED).append(Resource.LINEFEED).toString();

        super.builder
                .append(Resource.join(Resource.LINEFEED, super.floor))
                .append(String.format(super.room, columns.get().map(Column::nameWithType).toArray(String[]::new)))
                .append(super.floor);

        super.columnMapList.stream().forEach(coList -> {
            final String room = String.format(super.room, Arrays.stream(columns.get().map(Column::getName).toArray(String[]::new)).map(name ->
                    coList.get(name)).toArray(String[]::new));
            super.builder.append(room).append(super.floor);
        });

        if (columnMapList.isEmpty())
            super.builder.append(emptyFloor()).append(super.floor);


        return super.builder.toString();
    }

    private String emptyFloor() {
        int floorLength = this.floor.length() - 4 <= 0 ? 6 : this.floor.length() - 4;

        final String form = Resource.join(
                Resource.WALL, " %-",
                String.valueOf(floorLength), "s",
                Resource.WALL, Resource.LINEFEED);

        final String empty = String.format(form, EMPTY);

        return empty;
    }

    @Override
    void calculateColumnInfo() {
        Arrays.stream(this.fieldManager.getActivatedFields()).forEach(entry -> {
            try {

                String fieldName = (String) entry.getKey();
                String name = entry.getValue().getClass().getSimpleName();

                final Column newColumn = new Column(fieldName, name, super.optionAware.isNonDataType());
                columns.add(newColumn);
            } catch (ClassCastException ex) {
                throw new IllegalArgumentException("The Key of Map.class must be String.class.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        this.setFieldValues();
    }

    private void setFieldValues() {
        this.streamSupplier.get().forEach(row -> {
            Map.Entry<String, Object>[] fields = this.fieldManager.getActivatedFields();
            HashMap<String, String> columnMap = new HashMap<>();

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

    private String getStringValue(Object value, final Column column) {
        if (value == null)
            value = "(null)";

        String strValue = typeControl(value).replaceAll(IGNORE_LETTER, " ");
        Integer lengthOfValue = strValue.length();

        if (lengthOfValue > DEFAULT_MAX_LENGTH) {
            strValue = String.format("%s...", strValue.substring(0, (DEFAULT_MAX_LENGTH - 3)));
            lengthOfValue = DEFAULT_MAX_LENGTH;
        }

        column.setLength(Math.max(column.getLength(), (lengthOfValue + EACH_SPACE_LENGTH)));

        return strValue;

    }

    private String typeControl(Object value) {

        Object result;

        if (value instanceof ChronoLocalDate)
            result = ((ChronoLocalDate) value).format(optionAware.getDateFormatter());
        else if (value instanceof ChronoLocalDateTime)
            result = ((ChronoLocalDateTime) value).format(optionAware.getDateFormatter());
        else
            result = value;

        return result.toString();
    }

    @Override
    PrintableFieldManager getCurrentFieldManager() {
        return this.fieldManager;
    }


    private void setRoom() {
        final StringBuilder subBuilder = new StringBuilder();
        super.columns.stream().forEach((col) ->
                subBuilder.append(String.format("%s %%-%ds", Resource.WALL, (col.getLength() - 1))));

        subBuilder.append(Resource.join(Resource.WALL, Resource.LINEFEED));
        super.room = subBuilder.toString();
    }

    private void setFloor() {
        final StringBuilder subBuilder = new StringBuilder();
        subBuilder.append(Resource.APEX);

        super.columns.stream().forEach((col) -> {
            IntStream.range(0, col.getLength()).forEach((n) -> subBuilder.append(Resource.BRICK));
            subBuilder.append(Resource.APEX);
        });

        subBuilder.append(Resource.LINEFEED);
        super.floor = subBuilder.toString();
    }

    private Map<String, Object> getKeyModelWithInitStream(Object target) {
        Map<String, Object> keyModel;
        if(List.class.isAssignableFrom(target.getClass())) {
            List<Map<String, Object>> mapList = (List<Map<String, Object>>) target;
            keyModel = mapList.stream().findFirst().orElseThrow(() ->
                    new IllegalArgumentException("Map object must be has element at least one"));

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

