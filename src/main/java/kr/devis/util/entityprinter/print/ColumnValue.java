package kr.devis.util.entityprinter.print;

import kr.devis.util.entityprinter.util.CommonUtils;

public class ColumnValue {

    private final String value;
    private final String firstLine;
    private int lineCount = 0;
    private int lineLength = 0;
    private boolean isMultiLine;

    public ColumnValue(String original) {
        this.value = original;
        String[] lines = CommonUtils.separateWithLineFeed(original);
        CommonUtils.getWithSeparate(lines, (length) -> {
            this.lineCount = lines.length;
            this.lineLength = length;
            this.isMultiLine = (lineCount > 1);
        });
        this.firstLine = lines[0];
    }

    public String getValue() {
        return this.value;
    }

    public int getLineCount() {
        return this.lineCount;
    }

    public int getLineLength() {
        return this.lineLength;
    }

    public boolean isMultiLine() {
        return this.isMultiLine;
    }

    public String getFirstLine() {
        return this.firstLine;
    }
}
