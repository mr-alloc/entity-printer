package kr.devis.util.entityprinter.util;

import kr.devis.util.entityprinter.constant.Resource;
import kr.devis.util.entityprinter.print.Column;
import kr.devis.util.entityprinter.print.Wrapper;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface CommonUtils {


    static boolean isNull(final Object target) {

        return (target == null);
    }

    static boolean isNotNull(final Object target) {

        return (!isNull(target));
    }

    static void ifNull(final Object target, final Supplier<RuntimeException> behavior) {

        if (isNull(target))
            throw behavior.get();
    }

    static String[] columnValuesOf(List<Column> columns, Function<Column, String> columnNameFunction) {
        return columns.stream()
                .map(columnNameFunction)
                .toArray(String[]::new);
    }

    static void getWithSeparate(String[] lines, Consumer<Integer> lengthConsumer) {
        int maxLength = 0;
        for (String line : lines) {
            maxLength = Math.max(maxLength, line.length());
        }

        lengthConsumer.accept(maxLength);
    }

    static String[] separateWithLineFeed(String original) {
        return original.split(Resource.LINEFEED);
    }

    static <T> boolean isPrintableField(Class<T> clazz) {
        return (clazz.isEnum() || clazz.isPrimitive() || Wrapper.has(clazz.getSimpleName()));
    }

    static <T> boolean isPrintableEntity(Class<T> clazz) {
        return !isPrintableField(clazz);
    }

    static String[] separateWithSize(String strValue, int partitionSize) {
        if (strValue.length() < partitionSize) return new String[]{ strValue };

        int pageSize = (strValue.length() / partitionSize) + (strValue.length() % partitionSize == 0 ? 0 : 1);
        String[] lines = new String[pageSize];
        for (int i = 0; i < pageSize; i++) {
            int start = i * partitionSize;
            int end = Math.min(start + partitionSize, strValue.length());
            lines[i] = strValue.substring(start, end);
        }
        return lines;
    }

    static int getMaxLength(String[] lines) {
        return Arrays.stream(lines)
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }

    static String escapeWhiteSpace(String value) {
        return value.replace("\n", "\\n")
                .replace("\r", "")
                .replace("\t", "\\t");
    }
}
