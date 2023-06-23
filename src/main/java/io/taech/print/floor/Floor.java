package io.taech.print.floor;

import io.taech.constant.Resource;
import io.taech.print.Column;

import java.util.List;
import java.util.stream.Collectors;

import static io.taech.constant.Resource.BRICK;

public class Floor {

    private final List<Column> eachColumns;

    public Floor(List<Column> columns) {
        this.eachColumns = columns;
    }

    @Override
    public String toString() {
        return eachColumns.stream()
                .map(column -> {
                    StringBuilder subBuilder = new StringBuilder();
                    for (int i = 0; i < column.getLength(); i++) {
                        subBuilder.append(BRICK);
                    }
                    return subBuilder.toString();
                })
                .collect(Collectors.joining(Resource.APEX, Resource.APEX, Resource.APEX));
    }
}
