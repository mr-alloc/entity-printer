package io.taech.print;

import io.taech.constant.BuilderType;
import io.taech.constant.PrintOption;
import io.taech.print.builder.RowBuilder;
import io.taech.print.builder.RowBuilderProvider;

public class PrintConfigurator {

    private RowBuilder rowBuilder;

    public PrintConfigurator(final BuilderType type) {
        this.rowBuilder = RowBuilderProvider.getInstance().provide(type)
                .proceed(obj, typeClass);

    }

    public PrintConfigurator options(final PrintOption... options) {
        this.rowBuilder.options(options);

        return this;
    }

    public PrintConfigurator activateFields(final Integer... filedIndexes) {
        this.rowBuilder.activateFields(filedIndexes);
        return this;
    }

    RowBuilder apply() {
        return this.rowBuilder;
    }


}
