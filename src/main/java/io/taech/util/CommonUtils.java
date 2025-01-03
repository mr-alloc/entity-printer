package io.taech.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class CommonUtils {

    private CommonUtils() {}

    public static boolean isNull(final Object target) {

        return (target == null);
    }

    public static boolean isNotNull(final Object target) {

        return (! isNull(target));
    }

    public static void ifNull(final Object target, final Supplier<RuntimeException> behavior) {

        if(isNull(target))
            throw behavior.get();
    }

}
