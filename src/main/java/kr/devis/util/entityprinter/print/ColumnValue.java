package kr.devis.util.entityprinter.print;

import kr.devis.util.entityprinter.util.CommonUtils;

public class ColumnValue {

    private final String value;
    private String firstLine;
    private int lineCount;
    private int lineLength;

    public ColumnValue(String original) {
        this.value = original;
        this.lineCount = 1;
        this.lineLength = original.length();
        this.firstLine = original;
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

    public String getFirstLine() {
        return this.firstLine;
    }

    public void applyMultiline(String[] lines) {
        this.lineCount = lines.length;
        this.firstLine = lines[0];
    }
}
