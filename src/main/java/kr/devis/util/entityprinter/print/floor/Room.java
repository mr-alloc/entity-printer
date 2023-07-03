package kr.devis.util.entityprinter.print.floor;

import kr.devis.util.entityprinter.constant.Resource;
import kr.devis.util.entityprinter.print.Column;

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
        return this.eachColumns.stream()
                .map(column -> String.format(ROOM_FORMAT, column.getLength() - Resource.EACH_SPACE_LENGTH))
                .collect(Collectors.joining(Resource.INNER_WALL, Resource.SIDE_WALL, Resource.SIDE_WALL));
    }
}
