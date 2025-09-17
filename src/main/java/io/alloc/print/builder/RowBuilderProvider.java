package io.alloc.print.builder;

import io.alloc.constant.BuilderType;
import io.alloc.print.PrintConfigurator;

import java.lang.reflect.Constructor;

public class RowBuilderProvider {

    private static final RowBuilderProvider PROVIDER = new RowBuilderProvider();


    private RowBuilderProvider() {
    }

    public static RowBuilderProvider getInstance() {
        return PROVIDER;
    }

    @SuppressWarnings("unchecked")
    public <I> RowBuilder provide(final PrintConfigurator configurator) {
        try {
            final Class<? extends RowBuilder> clazz = BuilderType.get(configurator.getBuilderType());
            final Constructor<? extends RowBuilder> constructor = clazz.getConstructor();

            RowBuilder rowBuilder = constructor.newInstance();
            return rowBuilder.config(configurator);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
