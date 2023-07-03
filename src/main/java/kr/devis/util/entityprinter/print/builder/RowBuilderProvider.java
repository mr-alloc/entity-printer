package kr.devis.util.entityprinter.print.builder;

import kr.devis.util.entityprinter.constant.BuilderType;
import kr.devis.util.entityprinter.print.PrintConfigurator;

import java.lang.reflect.Constructor;

public class RowBuilderProvider {

    private static RowBuilderProvider provider = new RowBuilderProvider();

    private RowBuilderProvider() {
    }

    public static RowBuilderProvider getInstance() {
        return provider;
    }

    public <I> RowBuilder<I> provide(final PrintConfigurator<I> configurator) {
        RowBuilder<I> rowBuilder = null;
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
