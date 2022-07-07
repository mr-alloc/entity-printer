package io.taech;

import io.taech.constant.BuilderType;
import io.taech.constant.PrintOption;
import io.taech.print.EntityPrinter;
import io.taech.print.PrintConfigurator;

import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//
//        List<Coordinate> coordinates = new ArrayList<>();
//        coordinates.add(new Coordinate(1, 2));
//        coordinates.add(new Coordinate(2, 1));
//        coordinates.add(new Coordinate(4, 2));
//        coordinates.add(new Coordinate(4, 3));

        List<History> histories = new ArrayList<>();
        histories.add(new History(1L, "JOIN", "authenticate for join", "token-test-info"));

        final PrintConfigurator configurator = new PrintConfigurator(BuilderType.DEFAULT)
                .options(PrintOption.NO_DATA_TYPE, PrintOption.DATETIME_FORMAT)
                .dateformat(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm:ss"));

        final String result = EntityPrinter.draw(histories, History.class, configurator);
        System.out.println(result);

    }

}
