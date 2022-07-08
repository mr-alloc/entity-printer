package io.taech.constant;

import io.taech.print.builder.BasicRowBuilder;
import io.taech.print.builder.MapRowBuilder;
import io.taech.print.builder.RowBuilder;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum BuilderType {
    DEFAULT(BasicRowBuilder.class),
    MAP(MapRowBuilder.class)
    ;
    private Class<? extends RowBuilder> builderClass;
    private static final Map<BuilderType, Class<? extends RowBuilder>> builderStore = Arrays.stream(values())
            .collect(Collectors.toMap(Function.identity(), BuilderType::getBuilderClass));

    BuilderType(Class<? extends RowBuilder> builderClass) {
        this.builderClass = builderClass;
    }

    public Class<? extends RowBuilder> getBuilderClass() {
        return this.builderClass;
    }

    public static Class<? extends RowBuilder> get(final BuilderType builderType) {
        return builderStore.get(builderType);
    }

}
