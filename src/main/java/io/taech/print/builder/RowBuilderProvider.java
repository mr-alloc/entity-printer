package io.taech.print.builder;

import io.taech.constant.BuilderType;

import java.lang.reflect.Constructor;

public class RowBuilderProvider {

    private static RowBuilderProvider provider = new RowBuilderProvider();

    private RowBuilderProvider() {}

    public static RowBuilderProvider getInstance() {
        return provider;
    }

    public RowBuilder provide(final BuilderType builderType) {
        RowBuilder rowBuilder = null;
        try {

            final Class<? extends RowBuilder> clazz = BuilderType.get(builderType);
            final Constructor<? extends RowBuilder> constructor = clazz.getConstructor();
            rowBuilder = constructor.newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowBuilder;
    }
}
