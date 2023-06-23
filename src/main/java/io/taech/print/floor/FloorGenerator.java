package io.taech.print.floor;

import io.taech.print.Column;

import java.util.List;

public interface FloorGenerator {
    void generateSuiteFloor(List<Column> columns);

    SuiteFloor getSuiteFloor();
}
