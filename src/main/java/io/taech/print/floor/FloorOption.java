package io.taech.print.floor;

import io.taech.constant.Resource;
import io.taech.print.Column;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static io.taech.constant.Resource.BRICK;

public class FloorOption {

    public static Function<List<Column>, Floor> defaultFloor() {
        return ((columns) -> new Floor() {
            @Override
            public String toString() {
                return columns.stream()
                        .map(Column::getLength)
                        .map(len -> {
                            StringBuilder subBuilder = new StringBuilder();
                            for(int i = 0;i < len;i++) {
                                subBuilder.append(BRICK);
                            }
                            return subBuilder.toString();
                        })
                        .collect(Collectors.joining(Resource.APEX, Resource.APEX, Resource.APEX));
            }
        });
    }
}
