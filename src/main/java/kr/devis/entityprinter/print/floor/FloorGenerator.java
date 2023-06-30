package kr.devis.entityprinter.print.floor;

import kr.devis.entityprinter.print.Column;

import java.util.List;

public interface FloorGenerator {
    void generateSuiteFloor(List<Column> columns, int size);

    SuiteFloor getSuiteFloor();
}
