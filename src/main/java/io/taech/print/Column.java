package io.taech.print;

import io.taech.constant.Resource;

public class Column {
    private int length;
    private String name;
    private String type;

    public Column(final String name, final String type) {
        this.name = name;
        this.type = type;
        this.length = (nameWithType().length() + 2);
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
        return String.format("%s (%s)", this.name, this.type);
    }

}
