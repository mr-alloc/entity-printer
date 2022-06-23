package io.taech;

import io.taech.constant.BuilderType;
import io.taech.constant.PrintOption;
import io.taech.print.EntityPrinter;
import io.taech.print.PrintConfigurator;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate(1, 2));
        coordinates.add(new Coordinate(2, 1));
        coordinates.add(new Coordinate(4, 2));
        coordinates.add(new Coordinate(4, 3));
        PrintConfigurator configurator = new PrintConfigurator(BuilderType.DEFAULT)
                .options(PrintOption.NO_DATA_TYPE, PrintOption.EXCEPT_COLUMN)
                .activateFields(1,2,3);
        final String result = EntityPrinter.draw(coordinates, Coordinate.class, configurator);
        System.out.println(result);

    }

}
