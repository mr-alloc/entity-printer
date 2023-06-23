package io.taech.print;

import io.taech.constant.BuilderType;
import io.taech.print.builder.RowBuilderProvider;
import io.taech.util.CommonUtils;

import java.util.Map;


public interface EntityPrinter {

    static String draw(final Object obj, final Class<?> typeCLass) {
        return draw(obj, typeCLass, null);
    }

    static <I> String draw(final Object obj, final Class<?> typeClass, final PrintConfigurator<I> configurator) {
        final PrintConfigurator<I> toBeConfigured = inspectType(typeClass, configurator);

        return RowBuilderProvider.getInstance()
                .provide(toBeConfigured)
                .proceed(obj, typeClass)
                .build();
    }

    static <I> PrintConfigurator<I> inspectType(final Class<?> typeClass, final PrintConfigurator<I> configured) {
        PrintConfigurator<I> configurator = CommonUtils.isNull(configured)
                ? PrintConfigurator.create(BuilderType.DEFAULT)
                : configured;

        if (Map.class.isAssignableFrom(typeClass)) {
            configurator.builderType(BuilderType.MAP);
        }

        return configurator;
    }
}
