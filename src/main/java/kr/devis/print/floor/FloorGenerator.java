package kr.devis.print.floor;

import kr.devis.print.Column;

import java.util.List;

public interface FloorGenerator {
    void generateSuiteFloor(List<Column> columns, int size);

    SuiteFloor getSuiteFloor();
}
