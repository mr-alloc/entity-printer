package io.taech.print.floor;

import io.taech.print.Column;

import java.util.List;

public interface FloorGenerator {
    SuiteFloor generateSuiteFloor(List<Column> columns);
}
