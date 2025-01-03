package io.alloc.print;

import io.alloc.constant.PrintOption;
import io.alloc.util.CommonUtils;

import java.time.format.DateTimeFormatter;
import java.util.Set;

public class PrintOptionAware {

    private boolean nonDataType = false;
    private boolean exceptColumn = false;
    private boolean dateTimeFormat = false;
    private boolean allowMultiline = false;
    private boolean withoutFloor = false;
    private boolean noEscape = false;
    private boolean noEllipsis = false;

    private final Set<PrintOption> options;
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

    public PrintOptionAware(final Set<PrintOption> options) {
        this.options = options;
        activate();
    }

    public boolean isNonDataType() {
        return this.nonDataType;
    }

    public boolean isExceptColumn() {
        return this.exceptColumn;
    }

    public boolean hasDateTimeFormat() {
        return this.dateTimeFormat;
    }

    public boolean isAllowMultiline() {
        return this.allowMultiline;
    }

    public boolean isWithoutFloor() {
        return this.withoutFloor;
    }

    public boolean isNoEscape() {
        return this.noEscape;
    }

    public boolean isNoEllipsis() {
        return this.noEllipsis;
    }

    private void activate() {
        if (CommonUtils.isNull(options) || options.isEmpty())
            return;

        options.forEach(this::checkOption);
    }

    public void checkOption(final PrintOption option) {
        switch (option) {
            case NO_DATA_TYPE:
                this.nonDataType = true;
                break;
            case EXCEPT_COLUMN:
                this.exceptColumn = true;
                break;
            case DATE_FORMAT:
                this.dateTimeFormat = true;
                break;
            case ALLOW_MULTILINE:
                this.allowMultiline = true;
                break;
            case WITHOUT_EACH_BORDER_BOTTOM:
                this.withoutFloor = true;
                break;
            case NO_ESCAPE:
                this.noEscape = true;
                break;
            case NO_ELLIPSIS:
                this.noEllipsis = true;
                break;
            default:
                throw new IllegalArgumentException("Not found print option.");
        }
    }

    public void setDateFormatter(final DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public DateTimeFormatter getDateFormatter() {
        return this.formatter;
    }


}
