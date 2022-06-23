package io.taech.print;


public class Column {
    private int length;
    private String name;
    private String type;
    private boolean nonDataType = false;

    public Column(final String name, final String type, final boolean nonDataType) {
        this.name = name;
        this.type = type;
        this.length = ((nonDataType ? name.length() : nameWithType().length()) + 2);

    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return this.length;
    }

    public String getName() {
        return name;
    }

    public String nameWithType() {
        if(nonDataType)
            return this.name;

        return String.format("%s (%s)", this.name, this.type);
    }

}
