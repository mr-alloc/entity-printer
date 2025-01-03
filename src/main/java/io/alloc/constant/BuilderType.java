package io.alloc.constant;

import io.alloc.print.builder.BasicRowBuilder;
import io.alloc.print.builder.MappableRowBuilder;
import io.alloc.print.builder.RowBuilder;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum BuilderType {
    ROW(BasicRowBuilder.class),
    MAP(MappableRowBuilder.class);
    private Class<? extends RowBuilder> builderClass;
    private static final Map<BuilderType, Class<? extends RowBuilder>> BUILDER_STORE = Arrays.stream(values())
            .collect(Collectors.toMap(Function.identity(), BuilderType::getBuilderClass));

    BuilderType(Class<? extends RowBuilder> builderClass) {
        this.builderClass = builderClass;
    }

    public Class<? extends RowBuilder> getBuilderClass() {
        return this.builderClass;
    }

    public static Class<? extends RowBuilder> get(final BuilderType builderType) {
        return BUILDER_STORE.get(builderType);
    }

}
