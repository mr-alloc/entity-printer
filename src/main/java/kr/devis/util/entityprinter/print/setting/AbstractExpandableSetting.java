package kr.devis.util.entityprinter.print.setting;

import kr.devis.util.entityprinter.constant.PrintOption;
import kr.devis.util.entityprinter.print.PrintConfigurator;

import java.time.format.DateTimeFormatter;

public abstract class AbstractExpandableSetting<I> implements ExpandableSetting {

    protected abstract PrintConfigurator<I> activateFields(final I... fieldIndexes);

    protected abstract PrintConfigurator<I> dateformat(final DateTimeFormatter dateTimeFormatter);

    protected abstract PrintConfigurator<I> allowMultiLine();

    protected abstract PrintConfigurator<I> withoutFloor();

    protected abstract PrintConfigurator<I> excludeDataType();

    public abstract PrintConfigurator<I> expand(PrintOption... printOptions);

    public abstract PrintConfigurator<I> get();
}
