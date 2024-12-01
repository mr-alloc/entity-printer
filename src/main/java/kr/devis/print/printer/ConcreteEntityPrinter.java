package kr.devis.print.printer;

import kr.devis.constant.BuilderType;
import kr.devis.print.PrintConfigurator;
import kr.devis.print.builder.RowBuilderProvider;
import kr.devis.print.handle.KnownCondition;
import kr.devis.util.CommonUtils;

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
    public <I, T> String drawEntity(final T entity, final PrintConfigurator<I> configurator) {
        if (!CommonUtils.isPrintableEntity(entity.getClass()))
            return entity.toString();

        if (Collection.class.isAssignableFrom(entity.getClass())) {
            return KnownCondition.USE_COLLECTION_DRAW_METHOD;
        }

        return drawEntity(entity, configurator, entity.getClass());
    }

    private <I, T> String drawEntity(final T entity, final PrintConfigurator<I> configured, Class<?> clazz) {
        BuilderType builderType = Map.class.isAssignableFrom(clazz)
                ? BuilderType.MAP
                : BuilderType.ROW;

        return drawEntity(entity, configured, clazz, builderType);
    }

    private <I, T> String drawEntity(final T entity, final PrintConfigurator<I> configurator, Class<?> clazz, BuilderType builderType) {
        final PrintConfigurator<I> toBeConfigured = inspectType(configurator, builderType);

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
    public <I, T> String drawCollection(final Collection<? extends T> entities, final PrintConfigurator<I> configured, Class<? extends T> clazz) {
        BuilderType builderType = Map.class.isAssignableFrom(clazz)
                ? BuilderType.MAP
                : BuilderType.ROW;

        return drawCollection(entities, configured, clazz, builderType);
    }

    private <I, T> String drawCollection(final Collection<?> entities, final PrintConfigurator<I> configurator, Class<T> clazz, BuilderType builderType) {
        final PrintConfigurator<I> toBeConfigured = inspectType(configurator, builderType);

        return RowBuilderProvider.getInstance()
                .provide(toBeConfigured)
                .proceed(entities, clazz)
                .build();
    }

    private <I> PrintConfigurator<I> inspectType(final PrintConfigurator<I> configured, BuilderType builderType) {
        return CommonUtils.isNull(configured)
                ? PrintConfigurator.create(builderType)
                : configured.builderType(builderType);
    }
}
