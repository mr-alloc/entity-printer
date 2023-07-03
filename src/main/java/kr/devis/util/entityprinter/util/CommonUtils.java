package kr.devis.util.entityprinter.util;

import kr.devis.util.entityprinter.constant.Resource;
import kr.devis.util.entityprinter.print.Column;
import kr.devis.util.entityprinter.print.Wrapper;

import java.util.List;
import java.util.function.BiConsumer;
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

    static String[] columnValuesOf(List<Column> columns, Function<Column, String> columnNameSupplier) {
        return columns.stream()
                .map(columnNameSupplier)
                .toArray(String[]::new);
    }

    static void getWithSeparate(String[] lines, BiConsumer<Integer, Integer> valueConsumer) {
        int maxLength = 0;
        for (String line : lines) {
            maxLength = Math.max(maxLength, line.length());
        }

        valueConsumer.accept(lines.length, maxLength);
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
}
