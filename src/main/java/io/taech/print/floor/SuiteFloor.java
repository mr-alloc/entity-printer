package io.taech.print.floor;

import io.taech.constant.Resource;
import io.taech.print.Column;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.function.Function;

public class SuiteFloor {

    private final List<Column> columns;
    private Room room;
    private Floor floor;
    private int bufferSize;


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

    public ByteBuffer toByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(this.bufferSize);
        return null;
    }

    public void calculateBufferSize(int size) {
        // 헤더 길이 ByteBuffer.allocate() 용도
        int sumFloor = this.columns.stream().mapToInt(Column::getLength).sum();
        int divider = this.columns.size() + 1;

        int floorSize = (sumFloor + divider) * 3;

        int sumRoom = this.columns.stream().mapToInt(column -> column.getLength() + 2).sum();
        int divider2 = this.columns.size() + 1;

        int roomSize = (sumRoom + divider2) * (size + 1);
        System.out.println("expected roomSize = " + roomSize);

        int feedSize = size + 5;

        int totalSize = floorSize + roomSize + feedSize;
        System.out.println("expecred totalSize = " + totalSize);
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
