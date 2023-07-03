package kr.devis.util.entityprinter.print.floor;

import kr.devis.util.entityprinter.constant.Resource;
import kr.devis.util.entityprinter.print.Column;

import java.util.List;
import java.util.function.Function;

public class SuiteFloor {

    private final List<Column> columns;
    private Room room;
    private Floor floor;


    private SuiteFloor(List<Column> columns) {
        this.columns = columns;
    }

    public static Builder createWith(List<Column> columns) {
        return new Builder(columns);
    }

    public Room getRoom() {
        return room;
    }

    public Floor getFloor() {
        return this.floor;
    }

    public List<Column> getColumns() {
        return this.columns;
    }

    private String formatting(String[] columnNames) {
        return this.floor + Resource.LINEFEED +
                String.format(this.room.toString(), columnNames) + Resource.LINEFEED +
                this.floor + Resource.LINEFEED;
    }

    public String getFloorWithNames(String[] columnNames) {
        return formatting(columnNames);
    }

    public String getRoomWithValues(String[] columnValues) {
        return String.format(this.room.toString(), columnValues) + Resource.LINEFEED;
    }

    public String getFloorString() {
        return this.floor.toString() + Resource.LINEFEED;
    }


    public static class Builder {

        private final SuiteFloor suiteFloor;

        private Builder(List<Column> columns) {
            this.suiteFloor = new SuiteFloor(columns);
        }

        public Builder room(Function<List<Column>, Room> roomFunction) {
            this.suiteFloor.room = roomFunction.apply(this.suiteFloor.columns);
            return this;
        }

        public Builder floor(Function<List<Column>, Floor> floorFunction) {
            this.suiteFloor.floor = floorFunction.apply(this.suiteFloor.columns);
            return this;
        }

        public SuiteFloor build() {
            return this.suiteFloor;
        }
    }
}
