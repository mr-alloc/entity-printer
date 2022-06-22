package io.taech.print;

import io.taech.constant.BuilderType;
import io.taech.print.builder.RowBuilder;
import io.taech.print.builder.RowBuilderProvider;

public class EntityPrinter {

    public static String draw(Object obj, Class<?> typeClass) {
        final RowBuilder rowBuilder = RowBuilderProvider.getInstance()
                .provide(BuilderType.DEFAULT);

        rowBuilder.proceed(obj, typeClass);

        return rowBuilder.build();
    }
}
