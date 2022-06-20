package io.taech.print;

public class Column {
    private int length;
    private String name;

    public Column(int length, String name) {
        this.length = length;
        this.name = name;
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

}
