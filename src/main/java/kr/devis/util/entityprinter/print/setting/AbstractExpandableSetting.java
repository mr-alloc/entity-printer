package kr.devis.util.entityprinter.print.setting;

import kr.devis.util.entityprinter.constant.PrintOption;
import kr.devis.util.entityprinter.print.PrintConfigurator;

public abstract class AbstractExpandableSetting<I> implements ExpandableSetting {

    public abstract AbstractExpandableSetting<I> activateFields(final I... fieldIndexes);

    public abstract AbstractExpandableSetting<I> dateformat(final String pattern);

    public abstract AbstractExpandableSetting<I> allowMultiLine();

    public abstract AbstractExpandableSetting<I> withoutFloor();

    public abstract AbstractExpandableSetting<I> excludeDataType();

    public abstract AbstractExpandableSetting<I> expand(PrintOption... printOptions);

    public abstract AbstractExpandableSetting<I> noEscape();

    public abstract AbstractExpandableSetting<I> noEllipsis();

    public abstract PrintConfigurator<I> getConfig();
}
