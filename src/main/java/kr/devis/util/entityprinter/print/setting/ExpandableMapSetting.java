package kr.devis.util.entityprinter.print.setting;

import kr.devis.util.entityprinter.constant.BuilderType;
import kr.devis.util.entityprinter.constant.PrintOption;
import kr.devis.util.entityprinter.print.PrintConfigurator;

public class ExpandableMapSetting extends AbstractExpandableSetting<String> {

    private final PrintConfigurator<String> printConfigurator = PrintConfigurator.create(BuilderType.MAP);

    @Override
    public AbstractExpandableSetting<String> activateFields(String... columnNames) {
        this.printConfigurator.activateFields(columnNames);
        return this;
    }

    @Override
    public AbstractExpandableSetting<String> dateformat(String pattern) {
        this.printConfigurator.dateformat(pattern);
        return this;
    }

    @Override
    public AbstractExpandableSetting<String> allowMultiLine() {
        this.printConfigurator.allowMultiLine();
        return this;
    }

    @Override
    public AbstractExpandableSetting<String> withoutFloor() {
        this.printConfigurator.withoutFloor();
        return this;
    }

    @Override
    public AbstractExpandableSetting<String> excludeDataType() {
        this.printConfigurator.excludeDataType();
        return this;
    }

    @Override
    public AbstractExpandableSetting<String> expand(PrintOption... printOptions) {
        this.printConfigurator.applyAll(printOptions);
        return this;
    }

    @Override
    public AbstractExpandableSetting<String> noEscape() {
        this.printConfigurator.noEscape();
        return this;
    }

    @Override
    public AbstractExpandableSetting<String> noEllipsis() {
        this.printConfigurator.noEllipsis();
        return this;
    }

    @Override
    public PrintConfigurator<String> getConfig() {
        return this.printConfigurator;
    }

}
