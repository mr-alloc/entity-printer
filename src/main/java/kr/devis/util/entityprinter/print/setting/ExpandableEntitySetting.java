package kr.devis.util.entityprinter.print.setting;

import kr.devis.util.entityprinter.constant.PrintOption;
import kr.devis.util.entityprinter.print.PrintConfigurator;

import java.time.format.DateTimeFormatter;

public class ExpandableEntitySetting extends AbstractExpandableSetting<Integer> {

    private final PrintConfigurator<Integer> printConfigurator = PrintConfigurator.create();

    @Override
    public PrintConfigurator<Integer> activateFields(Integer... fieldIndexes) {
        return this.printConfigurator.activateFields(fieldIndexes);
    }

    @Override
    public PrintConfigurator<Integer> dateformat(DateTimeFormatter dateTimeFormatter) {
        return this.printConfigurator.dateformat(dateTimeFormatter);
    }

    @Override
    public PrintConfigurator<Integer> allowMultiLine() {
        return this.printConfigurator.allowMultiLine();
    }

    @Override
    public PrintConfigurator<Integer> withoutFloor() {
        return this.printConfigurator.withoutFloor();
    }

    @Override
    public PrintConfigurator<Integer> excludeDataType() {
        return this.printConfigurator.excludeDataType();
    }

    @Override
    public PrintConfigurator<Integer> expand(PrintOption... printOptions) {
        return this.printConfigurator.applyAll(printOptions);
    }

    @Override
    public PrintConfigurator<Integer> get() {
        return this.printConfigurator;
    }
}
