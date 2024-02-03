package kr.devis.util.entityprinter.print.setting;

import kr.devis.util.entityprinter.constant.PrintOption;
import kr.devis.util.entityprinter.print.PrintConfigurator;

public class ExpandableEntitySetting extends AbstractExpandableSetting<Integer> {

    private final PrintConfigurator<Integer> printConfigurator = PrintConfigurator.<Integer>create();

    @Override
    public ExpandableEntitySetting activateFields(Integer... fieldIndexes) {
        this.printConfigurator.activateFields(fieldIndexes);
        return this;
    }

    @Override
    public ExpandableEntitySetting dateformat(String pattern) {
        this.printConfigurator.dateformat(pattern);
        return this;
    }

    @Override
    public ExpandableEntitySetting allowMultiLine() {
        this.printConfigurator.allowMultiLine();
        return this;
    }

    @Override
    public ExpandableEntitySetting withoutFloor() {
        this.printConfigurator.withoutFloor();
        return this;
    }

    @Override
    public ExpandableEntitySetting excludeDataType() {
        this.printConfigurator.excludeDataType();
        return this;
    }

    @Override
    public ExpandableEntitySetting expand(PrintOption... printOptions) {
        this.printConfigurator.applyAll(printOptions);
        return this;
    }

    @Override
    public ExpandableEntitySetting noEscape() {
        this.printConfigurator.noEscape();
        return this;
    }

    @Override
    public ExpandableEntitySetting noEllipsis() {
        this.printConfigurator.noEllipsis();
        return this;
    }

    @Override
    public PrintConfigurator<Integer> getConfig() {
        return this.printConfigurator;
    }
}
