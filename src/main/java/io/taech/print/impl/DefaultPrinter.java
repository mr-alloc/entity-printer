package io.taech.print.impl;

import io.taech.print.Column;
import io.taech.print.EntityPrinter;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DefaultPrinter implements EntityPrinter {

    private static final int DEFAULT_MAX_COLUMN_LENGTH = 30;
    private static final int EACH_SPACE_LENGTH = 2;

    private static final String APEX = "+";
    private static final String WALL = "|";
    private static final String LINEFEED = "\n";
    private static final String ELLIPSIS = "...";
    private static final String DEFAULT_NULL_STRING = "(null)";

    private static final String PRINT_TARGET_IS_NULL = "This print target is null.";


    @Override
    public String draw(final Object obj) throws Exception {

        if (obj instanceof List) {
            List<Object> listObject = (List) obj;
            return printList(listObject);
        }

        return printList(Arrays.asList(obj));
    }

    private String printList(final List<Object> list) throws Exception {
        long start = System.currentTimeMillis();
        if (list.isEmpty())
            return PRINT_TARGET_IS_NULL;

        final StringBuilder builder = new StringBuilder();
        final List<Column> columns = new ArrayList<>();
        final List<Map<String, String>> columnMapList = new ArrayList<>();

        final Object o = list.get(0);
        setColumnData(Arrays.stream(o.getClass().getDeclaredFields()), columns);
        builder.append(String.format("\nList<%s> ↓\n", o.getClass().getSimpleName()));

        list.stream().forEach(obj -> {

            final Field[] fields = obj.getClass().getDeclaredFields();

            setFieldValues(obj, fields, columns, columnMapList);
        });


        //== 컬럼 명 ==//
        stackHeader(columns, builder);

        //== 컬럼 값 ==//
        stackBody(columns, columnMapList, builder);
        float result = (System.currentTimeMillis() - start) / 1000.0f;
        builder.append(String.format("Printed Stop Watch: %.4fsec\n", result));
        return builder.toString();
    }

    private void setColumnData(final Stream<Field> fields, final List<Column> columns) {
        fields.forEach(f -> {
            try {
                f.setAccessible(true);

                final String filedName = f.getName();
                final Integer fieldNameLength = filedName.getBytes(StandardCharsets.UTF_8).length;

                columns.add(new Column(fieldNameLength + EACH_SPACE_LENGTH, filedName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void setFieldValues(Object obj, Field [] fields, List<Column> columns, List<Map<String, String>> columnMapList) {
        Map<String, String> columnMap = new HashMap<>();
        IntStream.range(0, fields.length).forEach(i -> {
            try {
                final Field field = fields[i];
                field.setAccessible(true);

                Object value = field.get(obj);
                if (value == null)
                    value = DEFAULT_NULL_STRING;

                String strValue = value.toString().replaceAll("(\\n|\\r|\\t)", " ");

                final String fieldName = field.getName();
                Integer length = strValue.getBytes(StandardCharsets.UTF_8).length;
                final Column column = columns.get(i);
                if (length > DEFAULT_MAX_COLUMN_LENGTH) {
                    strValue = strValue.substring(0, (DEFAULT_MAX_COLUMN_LENGTH - ELLIPSIS.length())).concat(ELLIPSIS);
                    length = DEFAULT_MAX_COLUMN_LENGTH;
                }

                if (column.getLength() <= (length + EACH_SPACE_LENGTH))
                    column.setLength(length + EACH_SPACE_LENGTH);


                columnMap.put(fieldName, strValue);
            } catch(Exception skipped){}
        });
        columnMapList.add(columnMap);
    }

    private void stackLayer(final List<Column> columns, final StringBuilder builder) {
        builder.append(APEX);
        columns.stream().forEach(c -> {
            IntStream.range(0, c.getLength()).forEach((n) -> builder.append("-"));
            builder.append(APEX);
        });
        builder.append(LINEFEED);
    }

    private void stackHeader(final List<Column> columns, final StringBuilder builder) {
        stackLayer(columns, builder);
        columns.stream().forEach(c ->
                builder.append(String.format("%s %-" + (c.getLength() - 1) + "s", WALL, c.getName())));
        builder.append(WALL + LINEFEED);
        stackLayer(columns, builder);
    }

    private void stackBody(final List<Column> columns, final List<Map<String, String>> columnMapList, final StringBuilder builder) {
        columnMapList.stream().forEach((cm) -> {
            columns.stream().forEach(c ->
                builder.append(String.format("%s %-" + (c.getLength() - 1) + "s", WALL, cm.get(c.getName()))));
            builder.append(WALL + LINEFEED);
            stackLayer(columns, builder);
        });
    }

    

}
