package io.alloc.print.builder;

import io.alloc.constant.BuilderType;
import io.alloc.print.PrintConfigurator;

import java.lang.reflect.Constructor;

public class RowBuilderProvider {

    private static RowBuilderProvider provider = new RowBuilderProvider();

    private RowBuilderProvider() {
    }

    public static RowBuilderProvider getInstance() {
        return provider;
    }

    public <I> RowBuilder<I> provide(final PrintConfigurator<I> configurator) {
        try {
            final Class<? extends RowBuilder> clazz = BuilderType.get(configurator.getBuilderType());
            final Constructor<? extends RowBuilder> constructor = clazz.getConstructor();

            RowBuilder<I> rowBuilder = constructor.newInstance();
            return rowBuilder.config(configurator);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
