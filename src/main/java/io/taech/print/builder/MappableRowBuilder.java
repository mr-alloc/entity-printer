package io.taech.print.builder;

import io.taech.print.Column;
import io.taech.print.field.manager.PrintableFieldManager;
import io.taech.print.field.manager.PrintableMapManager;

import java.nio.ByteBuffer;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;
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
        super.initialize();
        return this;
    }

    @Override
    public String build() {
        final Supplier<Stream<Column>> columns = () -> super.columns.stream();
        if (this.fieldManager.getActivatedFields().length == 0)
            return builder.append(NO_ACTIVATED).append(LINEFEED).toString();

        super.builder
                .append(join(LINEFEED, super.floor))
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

        final String form = join(
                SIDE_WALL, " %-",
                String.valueOf(floorLength), "s",
                SIDE_WALL, LINEFEED);

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

    private String getStringValue(Object value, final Column column) {
        if (value == null) value = "(null)";

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


    @Override
    protected void setRoom() {
        final StringBuilder subBuilder = new StringBuilder();
        subBuilder.append(SIDE_WALL);

        for (int i = 0;i < super.columns.size();i++) {
            Integer len = super.columns.get(i).getLength() - 1;
            ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[10]);

            if (i == 0) {
                byteBuffer.put(0, PERCENT_BYTE);
                byteBuffer.put(1, LETTER_S_BYTE);

                subBuilder.append(String.format(" %%-%ds", len));
                continue;
            }
            subBuilder.append(String.format("%s %%-%ds", WALL, len));

            byteBuffer.put(2, SPACE_BYTE);
            byteBuffer.put(3, PERCENT_BYTE);
            byteBuffer.put(4, BRICK_BYTE);
            byteBuffer.putInt(5, len);
            byteBuffer.put(9, LETTER_S_BYTE);

            String str = new String(byteBuffer.array());
            System.out.println("str = " + str);
        }

        subBuilder.append(join(SIDE_WALL, LINEFEED));
        super.room = subBuilder.toString();
    }

    @Override
    protected void setFloor() {
        final StringBuilder subBuilder = new StringBuilder();
        subBuilder.append(APEX);
        for (Column column : super.columns) {
            byte[] buffer = new byte[column.getLength()];
            byte[] bytes = BRICK.getBytes();
            System.out.println("bytes = " + Arrays.toString(bytes));
        }
        super.columns.stream().forEach((col) -> {
            IntStream.range(0, col.getLength()).forEach((n) -> subBuilder.append(BRICK));
            subBuilder.append(APEX);
        });

        subBuilder.append(LINEFEED);
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

