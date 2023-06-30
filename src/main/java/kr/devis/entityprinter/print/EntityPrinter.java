package kr.devis.entityprinter.print;

import kr.devis.entityprinter.constant.BuilderType;
import kr.devis.entityprinter.print.builder.RowBuilderProvider;
import kr.devis.entityprinter.print.handle.KnownCondition;
import kr.devis.entityprinter.util.CommonUtils;

import java.util.Collection;
import java.util.Map;


public class EntityPrinter {

    public <T> String draw(final T entity) {
        if (!CommonUtils.isPrintableEntity(entity.getClass()))
            return entity.toString();

        return draw(entity, null);
    }

    public <I, T> String draw(final T entity, final PrintConfigurator<I> configurator) {
        if (!CommonUtils.isPrintableEntity(entity.getClass()))
            return entity.toString();

        if (Collection.class.isAssignableFrom(entity.getClass())) {
            return KnownCondition.USE_COLLECTION_DRAW_METHOD;
        }

        return drawEntity(entity, configurator, entity.getClass());
    }

    public <I> String draw(final Collection<?> entities, final PrintConfigurator<I> configurator, Class<?> innerType) {
        if (Collection.class.isAssignableFrom(innerType)) {
            return KnownCondition.CANNOT_USE_COLLECTION_AS_INNER_TYPE;
        }

        return drawCollection(entities, configurator, innerType);
    }

    private <I, T> String drawCollection(final Collection<?> entities, final PrintConfigurator<I> configured, Class<T> clazz) {
        BuilderType builderType = Map.class.isAssignableFrom(clazz)
                ? BuilderType.MAP
                : BuilderType.DEFAULT;

        return drawCollection(entities, configured, clazz, builderType);
    }

    private <I, T> String drawEntity(final T entity, final PrintConfigurator<I> configured, Class<?> clazz) {
        BuilderType builderType = Map.class.isAssignableFrom(clazz)
                ? BuilderType.MAP
                : BuilderType.DEFAULT;
        return drawEntity(entity, configured, clazz, builderType);
    }

    public <I, T> String drawCollection(final Collection<?> entities, final PrintConfigurator<I> configurator, Class<T> clazz, BuilderType builderType) {
        final PrintConfigurator<I> toBeConfigured = inspectType(configurator, builderType);

        return RowBuilderProvider.getInstance()
                .provide(toBeConfigured)
                .proceed(entities, clazz)
                .build();
    }

    public <I, T> String drawEntity(final T entity, final PrintConfigurator<I> configurator, Class<?> clazz, BuilderType builderType) {
        final PrintConfigurator<I> toBeConfigured = inspectType(configurator, builderType);

        return RowBuilderProvider.getInstance()
                .provide(toBeConfigured)
                .proceed(entity, clazz)
                .build();
    }

    public <I, T> PrintConfigurator<I> inspectType(final PrintConfigurator<I> configured, BuilderType builderType) {
        return CommonUtils.isNull(configured)
                ? PrintConfigurator.create(builderType)
                : configured.builderType(builderType);
    }
}
