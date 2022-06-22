package io.taech.print.builder;

import io.taech.constant.Resource;
import io.taech.excepted.PrintException;
import io.taech.print.Column;
import io.taech.print.Wrapper;
import io.taech.util.StopWatch;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class BasicRowBuilder extends AbstractRowBuilder {

    private static final String IGNORE_LETTER = "(\\n|\\r|\\t)";
    private static final Integer DEFAULT_MAX_LENGTH = 30;
    private static final Integer EACH_SPACE_LENGTH = 2;
    final String NODATA = "(no data)";

    @Override
    public RowBuilder proceed(final Object target, Class<?> typeClass) {
        super.initialize(target, typeClass);
        this.setting();
        return this;
    }


    @Override
    public String build() {
        final String[] colNames = super.columns.stream().map(Column::getName).toArray(String[]::new);
        super.builder
                .append(Resource.join(Resource.LINEFEED, super.floor))
                .append(String.format(super.room, colNames))
                .append(super.floor);

        super.columnMapList.stream().forEach(coList -> {
            final String room = String.format(super.room, Arrays.stream(colNames).map(name ->
                    coList.get(name)).toArray(String[]::new));
            builder.append(room).append(super.floor);
        });

        if(columnMapList.isEmpty())
            builder.append(emptyFloor());

        return builder.toString();
    }

    @Override
    void calculateColumnInfo() {
        Arrays.stream(super.targetClass.getDeclaredFields()).filter(field ->
                isPrintable(field)
        ).forEach(field -> {
            try {
                field.setAccessible(true);

                final String fieldName = field.getName();
                columns.add(new Column((fieldName.length() + 2), fieldName));
            } catch (Exception e) {
                e.printStackTrace();
                throw new PrintException(e.getMessage());
            }
        });

        setFieldValues();
    }

    private void setFieldValues() {
        super.streamSupplier.get().filter(row -> super.targetClass.equals(row.getClass())).forEach(row -> {
            final Field [] fields = row.getClass().getDeclaredFields();
            final Map<String, String> columnMap = new HashMap<>();
            IntStream.range(0, fields.length).filter(idx -> isPrintable(fields[idx])).forEach(idx -> {
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

    private boolean isPrintable(final Field field) {

        return (field.getType().isEnum() || Wrapper.has(field.getType().getSimpleName()));
    }

    private String getStringValue(Object value, final Column column) {
        if(value == null)
            value = "(null)";

        String strValue = value.toString().replaceAll(IGNORE_LETTER, " ");
        Integer lengthOfValue = strValue.length();
        if(lengthOfValue > DEFAULT_MAX_LENGTH) {
            strValue = String.format("%s...", strValue.substring(0, (DEFAULT_MAX_LENGTH - 3)));
            lengthOfValue = DEFAULT_MAX_LENGTH;
        }

        column.setLength(Math.max(column.getLength(), (lengthOfValue + EACH_SPACE_LENGTH)));

        return strValue;
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
        final int halfOfMessage = NODATA.length() / 2;
        final String left = Resource.join("%", String.valueOf((this.floor.length() / 2) + halfOfMessage), "s");
        String.format(left, NODATA);
        return null;
    }
}
