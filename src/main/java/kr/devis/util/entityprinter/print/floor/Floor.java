package kr.devis.util.entityprinter.print.floor;

import kr.devis.util.entityprinter.constant.Resource;
import kr.devis.util.entityprinter.print.Column;

import java.util.List;
import java.util.stream.Collectors;

import static kr.devis.util.entityprinter.constant.Resource.BRICK;

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
