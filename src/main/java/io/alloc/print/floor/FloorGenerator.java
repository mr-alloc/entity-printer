package io.alloc.print.floor;

import io.alloc.print.Column;

import java.util.List;

public interface FloorGenerator {
    void generateSuiteFloor(List<Column> columns, int size);

    SuiteFloor getSuiteFloor();
}
