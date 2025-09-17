package io.alloc.print.setting;

import io.alloc.constant.PrintOption;
import io.alloc.print.PrintConfigurator;

public class ExpandableEntitySetting extends AbstractExpandableSetting {

    private final PrintConfigurator printConfigurator = PrintConfigurator.create();

    @Override
    public ExpandableEntitySetting activateFields(String... fieldNames) {
        this.printConfigurator.activateFields(fieldNames);
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
    public ExpandableEntitySetting expand(PrintOption... printOptions) {
        this.printConfigurator.applyAll(printOptions);
        return this;
    }

    @Override
    public PrintConfigurator getConfig() {
        return this.printConfigurator;
    }
}
