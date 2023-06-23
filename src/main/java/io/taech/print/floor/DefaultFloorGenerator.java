package io.taech.print.floor;

import io.taech.print.Column;

import java.util.List;

public class DefaultFloorGenerator implements FloorGenerator {

    private SuiteFloor suiteFloor;

    @Override
    public void generateSuiteFloor(List<Column> columns) {

        this.suiteFloor = SuiteFloor.createWith(columns)
                .room(FloorOption.defaultRoom())
                .floor(FloorOption.defaultFloor())
                .build();
    }

    @Override
    public SuiteFloor getSuiteFloor() {
        return this.suiteFloor;
    }
}
