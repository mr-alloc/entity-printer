package io.alloc.print.printer;

import io.alloc.constant.BuilderType;
import io.alloc.print.PrintConfigurator;
import io.alloc.print.builder.RowBuilderProvider;
import io.alloc.print.handle.KnownCondition;
import io.alloc.util.CommonUtils;

import java.util.Collection;
import java.util.Map;


class ConcreteEntityPrinter implements IEntityPrinter {

    @Override
    public <T> String drawEntity(final T entity) {
        if (!CommonUtils.isPrintableEntity(entity.getClass()))
            return entity.toString();

        return drawEntity(entity, null);
    }

    @Override
    public <T> String drawEntity(final T entity, final PrintConfigurator configurator) {
        if (!CommonUtils.isPrintableEntity(entity.getClass()))
            return entity.toString();

        if (Collection.class.isAssignableFrom(entity.getClass())) {
            return KnownCondition.USE_COLLECTION_DRAW_METHOD;
        }

        return drawEntity(entity, configurator, entity.getClass());
    }

    private <I, T> String drawEntity(final T entity, final PrintConfigurator configured, Class<?> clazz) {
        BuilderType builderType = Map.class.isAssignableFrom(clazz)
                ? BuilderType.MAP
                : BuilderType.ROW;

        return drawEntity(entity, configured, clazz, builderType);
    }

    private <I, T> String drawEntity(final T entity, final PrintConfigurator configurator, Class<?> clazz, BuilderType builderType) {
        final PrintConfigurator toBeConfigured = inspectType(configurator, builderType);

        return RowBuilderProvider.getInstance()
                .provide(toBeConfigured)
                .proceed(entity, clazz)
                .build();
    }

    @Override
    public <T> String drawCollection(final Collection<? extends T> entities, Class<? extends T> clazz) {
        return drawCollection(entities, null, clazz);
    }

    @Override
    public <T> String drawCollection(final Collection<? extends T> entities, final PrintConfigurator configured, Class<? extends T> clazz) {
        BuilderType builderType = Map.class.isAssignableFrom(clazz)
                ? BuilderType.MAP
                : BuilderType.ROW;

        return drawCollection(entities, configured, clazz, builderType);
    }

    private <T> String drawCollection(final Collection<?> entities, final PrintConfigurator configurator, Class<T> clazz, BuilderType builderType) {
        final PrintConfigurator toBeConfigured = inspectType(configurator, builderType);

        return RowBuilderProvider.getInstance()
                .provide(toBeConfigured)
                .proceed(entities, clazz)
                .build();
    }

    private <I> PrintConfigurator inspectType(final PrintConfigurator configured, BuilderType builderType) {
        return CommonUtils.isNull(configured)
                ? PrintConfigurator.create(builderType)
                : configured.builderType(builderType);
    }
}
