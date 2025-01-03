package io.alloc.print.floor;

import io.alloc.print.Column;

import java.util.List;
import java.util.function.Function;

public interface FloorOption {

    static Function<List<Column>, Floor> defaultFloor() {
        return (Floor::new);
    }

    static Function<List<Column>, Room> defaultRoom() {
        return (Room::new);
    }


}
