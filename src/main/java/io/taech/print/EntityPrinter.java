package io.taech.print;

import io.taech.constant.BuilderType;
import io.taech.print.builder.RowBuilder;
import io.taech.print.builder.RowBuilderProvider;
import io.taech.util.CommonUtils;

public class EntityPrinter {

    public static String draw(final Object obj, final Class<?> typeCLass) {
        return draw(obj, typeCLass, null);
    }

    public static String draw(final Object obj, final Class<?> typeClass, PrintConfigurator configurator) {
        if(CommonUtils.isNull(configurator))
            configurator = new PrintConfigurator();

        return RowBuilderProvider.getInstance()
                .provide(configurator)
                .proceed(obj, typeClass)
                .build();
    }
}
