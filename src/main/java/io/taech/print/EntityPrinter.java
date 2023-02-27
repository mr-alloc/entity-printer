package io.taech.print;

import io.taech.constant.BuilderType;
import io.taech.constant.PrintOption;
import io.taech.print.builder.RowBuilderProvider;
import io.taech.util.CommonUtils;

import java.util.Map;


public class EntityPrinter {

    public static String draw(final Object obj, final Class<?> typeCLass) {
        return draw(obj, typeCLass, null);
    }

    public static String draw(final Object obj, final Class<?> typeClass, final PrintConfigurator configurator) {
        final PrintConfigurator toBeConfigured = inspectType(typeClass, configurator);

        return RowBuilderProvider.getInstance()
                .provide(toBeConfigured)
                .proceed(obj, typeClass)
                .build();
    }

    public static PrintConfigurator inspectType(final Class<?> typeClass, final PrintConfigurator configured) {
        PrintConfigurator configurator = CommonUtils.isNull(configured)
                ? PrintConfigurator.create(BuilderType.DEFAULT)
                : configured;

        if(Map.class.isAssignableFrom(typeClass)) {
            configurator.builderType(BuilderType.MAP);
        } else {
            configurator.builderType(BuilderType.DEFAULT);
        }

        return configurator;
    }
}
