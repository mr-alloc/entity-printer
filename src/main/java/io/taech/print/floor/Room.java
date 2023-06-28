package io.taech.print.floor;

import io.taech.constant.Resource;
import io.taech.print.Column;

import java.util.List;
import java.util.stream.Collectors;

public class Room {

    private static final String ROOM_FORMAT = " %%-%ds ";

    private final List<Column> eachColumns;


    public Room(List<Column> columns) {
        this.eachColumns = columns;
    }


    @Override
    public String toString() {
        String collect = this.eachColumns.stream()
                .map(column -> String.format(ROOM_FORMAT, column.getLength() - Resource.EACH_SPACE_LENGTH))
                .collect(Collectors.joining(Resource.INNER_WALL, Resource.SIDE_WALL, Resource.SIDE_WALL));

        return collect;
    }
}
