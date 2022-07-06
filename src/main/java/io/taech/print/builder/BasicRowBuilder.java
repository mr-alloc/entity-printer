package io.taech.print.builder;

import io.taech.constant.Resource;
import io.taech.print.Column;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BasicRowBuilder extends AbstractRowBuilder {

    private static final String IGNORE_LETTER = "(\\n|\\r|\\t)";
    private static final Integer DEFAULT_MAX_LENGTH = 30;
    private static final Integer EACH_SPACE_LENGTH = 2;

    private final String EMPTY = "empty";
    private final String NO_ACTIVATED = "There are no Activated fields";

    @Override
    public RowBuilder proceed(final Object target, Class<?> typeClass) {
        super.initialize(target, typeClass);
        this.setting();
        return this;
    }

    @Override
    public String build() {
        final Supplier<Stream<Column>> columns = () -> super.columns.stream();
        if(super.fieldManager.getActivatedFields().length == 0)
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

    @Override
    void calculateColumnInfo() {
        Arrays.stream(super.fieldManager.getActivatedFields()).forEach(field -> {
            try {
                field.setAccessible(true);

                final String fieldName = field.getName();
                final String name = field.getType().getSimpleName();

                final Column newColumn = new Column(fieldName, name, super.optionAware.isNonDataType());
                columns.add(newColumn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        setFieldValues();
    }

    private void setFieldValues() {
        super.streamSupplier.get()
                .filter(row -> super.fieldManager.getTypeClass().equals(row.getClass())).forEach(row -> {
                    final Field [] fields = super.fieldManager.getActivatedFields();
                    final Map<String, String> columnMap = new HashMap<>();

                    IntStream.range(0, fields.length).forEach(idx -> {
                        try {
                            final Field field = fields[idx];
                            field.setAccessible(true);

                            final Column column = columns.get(idx);
                            final String fieldName = field.getName();
                            final String strValue = getStringValue(field.get(row), column);

                            columnMap.put(fieldName, strValue);
                        } catch(Exception skipped) {}
                    });
                    super.columnMapList.add(columnMap);
                });
    }

    private String getStringValue(Object value, final Column column) {
        if(value == null)
            value = "(null)";

        String strValue = typeControl(value).replaceAll(IGNORE_LETTER, " ");
        Integer lengthOfValue = strValue.length();

        if(lengthOfValue > DEFAULT_MAX_LENGTH) {
            strValue = String.format("%s...", strValue.substring(0, (DEFAULT_MAX_LENGTH - 3)));
            lengthOfValue = DEFAULT_MAX_LENGTH;
        }

        column.setLength(Math.max(column.getLength(), (lengthOfValue + EACH_SPACE_LENGTH)));

        return strValue;
    }

    private String typeControl(final Object value) {

        Object result;

        if(value instanceof ChronoLocalDate)
            result = ((ChronoLocalDate) value).format(optionAware.getDateFormatter());
        else if(value instanceof ChronoLocalDateTime)
            result = ((ChronoLocalDateTime) value).format(optionAware.getDateFormatter());
        else
            result = value;

        return result.toString();
    }

    private void setting() {
        this.setFloor();
        this.setRoom();
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

    private void setRoom() {
        final StringBuilder subBuilder = new StringBuilder();
        super.columns.stream().forEach((col) ->
            subBuilder.append(String.format("%s %%-%ds", Resource.WALL, (col.getLength() - 1))));

        subBuilder.append(Resource.join(Resource.WALL, Resource.LINEFEED));
        super.room = subBuilder.toString();
    }

    private String emptyFloor() {
        int floorLength = this.floor.length() -4 <= 0 ? 6 : this.floor.length() -4;

        final String form = Resource.join(
                Resource.WALL, " %-",
                String.valueOf(floorLength), "s",
                Resource.WALL, Resource.LINEFEED);

        final String empty = String.format(form, EMPTY);

        return empty;
    }
}
