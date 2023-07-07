package kr.devis.util.entityprinter.print.setting;

import kr.devis.util.entityprinter.constant.BuilderType;
import kr.devis.util.entityprinter.constant.PrintOption;
import kr.devis.util.entityprinter.print.PrintConfigurator;

import java.time.format.DateTimeFormatter;

public class ExpandableMapSetting extends AbstractExpandableSetting<String> {

    private final PrintConfigurator<String> printConfigurator = PrintConfigurator.create(BuilderType.MAP);

    @Override
    public PrintConfigurator<String> activateFields(String... fieldIndexes) {
        return this.printConfigurator.activateFields(fieldIndexes);
    }

    @Override
    public PrintConfigurator<String> dateformat(DateTimeFormatter dateTimeFormatter) {
        return this.printConfigurator.dateformat(dateTimeFormatter);
    }

    @Override
    public PrintConfigurator<String> allowMultiLine() {
        return this.printConfigurator.allowMultiLine();
    }

    @Override
    public PrintConfigurator<String> withoutFloor() {
        return this.printConfigurator.withoutFloor();
    }

    @Override
    public PrintConfigurator<String> excludeDataType() {
        return this.printConfigurator.excludeDataType();
    }

    @Override
    public PrintConfigurator<String> expand(PrintOption... printOptions) {
        return this.printConfigurator.applyAll(printOptions);
    }

    @Override
    public PrintConfigurator<String> get() {
        return this.printConfigurator;
    }

}
