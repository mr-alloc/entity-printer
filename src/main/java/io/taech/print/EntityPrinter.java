package io.taech.print;

import io.taech.constant.BuilderType;
import io.taech.print.builder.RowBuilderProvider;
import io.taech.util.CommonUtils;

import java.util.Collection;
import java.util.Map;


public interface EntityPrinter {


    static <T> String draw(final T entity, Class<T> t) {
        return draw(entity, null, t);
    }

    static <I, T> String draw(final T entity, final PrintConfigurator<I> configured, Class<T> clazz) {
        BuilderType builderType = Map.class.isAssignableFrom(clazz)
                ? BuilderType.MAP
                : BuilderType.DEFAULT;
        return draw(entity, configured, clazz, builderType);
    }

    static <T> String draw(final Collection<T> entities, Class<T> clazz) {
        return draw(entities, null, clazz);
    }

    static <I, T> String draw(final Collection<T> entities, final PrintConfigurator<I> configured, Class<T> clazz) {
        BuilderType builderType = Map.class.isAssignableFrom(clazz)
                ? BuilderType.MAP
                : BuilderType.DEFAULT;

        return draw(entities, configured, clazz, builderType);
    }

    static <I, T> String draw(final T entity, final PrintConfigurator<I> configurator, Class<T> clazz, BuilderType builderType) {
        final PrintConfigurator<I> toBeConfigured = inspectType(configurator, builderType);

        return RowBuilderProvider.getInstance()
                .provide(toBeConfigured)
                .proceed(entity, clazz)
                .build();
    }

    static <I, T> String draw(final Collection<T> entities, final PrintConfigurator<I> configurator, Class<T> clazz, BuilderType builderType) {
        final PrintConfigurator<I> toBeConfigured = inspectType(configurator, builderType);

        return RowBuilderProvider.getInstance()
                .provide(toBeConfigured)
                .proceed(entities, clazz)
                .build();
    }

    static <I, T> PrintConfigurator<I> inspectType(final PrintConfigurator<I> configured, BuilderType builderType) {
        return CommonUtils.isNull(configured)
                ? PrintConfigurator.create(builderType)
                : configured.builderType(builderType);
    }
}
