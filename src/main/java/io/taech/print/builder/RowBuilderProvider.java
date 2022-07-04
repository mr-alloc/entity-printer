package io.taech.print.builder;

import io.taech.constant.BuilderType;
import io.taech.print.PrintConfigurator;

import java.lang.reflect.Constructor;

public class RowBuilderProvider {

    private static RowBuilderProvider provider = new RowBuilderProvider();

    private RowBuilderProvider() {}

    public static RowBuilderProvider getInstance() {
        return provider;
    }

    public RowBuilder provide(final PrintConfigurator configurator) {
        RowBuilder rowBuilder = null;
        try {

            final Class<? extends RowBuilder> clazz = BuilderType.get(configurator.getBuilderType());
            final Constructor<? extends RowBuilder> constructor = clazz.getConstructor();

            rowBuilder = constructor.newInstance();
            rowBuilder.config(configurator);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowBuilder;
    }
}
