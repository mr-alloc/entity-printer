package io.taech;

import io.taech.constant.BuilderType;
import io.taech.constant.PrintOption;
import io.taech.print.EntityPrinter;
import io.taech.print.PrintConfigurator;

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
        histories.add(new History(1L, "JOIN", "회원가입 인증", "token-test-info"));

        PrintConfigurator configurator = new PrintConfigurator(BuilderType.DEFAULT)
                .options(PrintOption.NO_DATA_TYPE, PrintOption.KOREAN_MODE);
        final String result = EntityPrinter.draw(histories, History.class, configurator);
        System.out.println(result);

    }

}
