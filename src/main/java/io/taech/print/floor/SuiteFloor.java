package io.taech.print.floor;

import io.taech.constant.Resource;
import io.taech.print.Column;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class SuiteFloor implements Floor {

    private List<Column> columns;
    private String room = "";
    private String floor = "";

    private SuiteFloor() {
    }

    public static Builder createWith(List<Column> columns) {
        return new Builder(columns);
    }

    public static class Builder {

        private SuiteFloor suiteFloor;
        private Builder(List<Column> columns) {
            this.suiteFloor = new SuiteFloor();
            this.suiteFloor.columns = columns;
        }

        public Builder room(Function<Column, Integer> roomFunction) {
            this.suiteFloor.room = this.suiteFloor.columns.stream()
                    .map(roomFunction)
                    .map(len -> String.format(" %%-%ds ", len - Resource.EACH_SPACE_LENGTH))
                    .collect(Collectors.joining(Resource.WALL, Resource.SIDE_WALL, Resource.SIDE_WALL));
            return this;
        }

        public Builder floor(Function<List<Column>, Floor> floorFunction) {
            this.suiteFloor.floor = floorFunction.apply(this.suiteFloor.columns).toString();
            return this;
        }

        public SuiteFloor build() {
            return this.suiteFloor;
        }
    }
}
