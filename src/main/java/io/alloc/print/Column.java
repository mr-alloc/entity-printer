package io.alloc.print;


public class Column {
    private int length;
    private int line;
    private final String name;
    private final String type;
    private final boolean nonDataType;

    public Column(final String name, final String type, final boolean nonDataType) {
        this.name = name;
        this.type = type;
        this.length = ((nonDataType ? name.length() : nameWithType().length()) + 2);
        this.nonDataType = nonDataType;
    }

    public int getLine() {
        return this.line;
    }

    public int getLength() {
        return this.length;
    }

    public String getName() {
        return this.name;
    }

    public String nameWithType() {
        if(nonDataType)
            return this.name;

        return String.format("%s (%s)", this.name, this.type);
    }

    public void with(int length, int line) {
        this.length = length;
        this.line = line;
    }

}
