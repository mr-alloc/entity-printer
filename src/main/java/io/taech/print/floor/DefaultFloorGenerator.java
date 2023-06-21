package io.taech.print.floor;

import io.taech.print.Column;

import java.util.List;

public class DefaultFloorGenerator implements FloorGenerator {

    @Override
    public SuiteFloor generateSuiteFloor(List<Column> columns) {

        //SIDE_WALL
        SuiteFloor.createWith(columns)
                .room(Column::getLength)
                .floor(FloorOption.defaultFloor())
                .build();
        return null;
    }

}
