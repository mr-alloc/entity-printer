package io.taech.print;

import io.taech.util.CommonUtils;

public class ColumnValue {

    private final String value;
    private Integer lineCount;
    private Integer lineLength;
    private boolean isMultiLine;

    private String firstLine;

    public ColumnValue(String original) {
        this.value = original;
        String[] lines = CommonUtils.separateWithLineFeed(original);
        CommonUtils.getWithSeparate(lines, (count, length) -> {
            this.lineCount = count;
            this.lineLength = length;
            this.isMultiLine = (count > 1);
        });
        this.firstLine = lines[0];
    }

    public String getValue() {
        return this.value;
    }

    public Integer getLineCount() {
        return this.lineCount;
    }

    public Integer getLineLength() {
        return this.lineLength;
    }

    public boolean isMultiLine() {
        return this.isMultiLine;
    }

    public String getFirstLine() {
        return this.firstLine;
    }
}
