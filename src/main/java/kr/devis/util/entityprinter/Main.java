package kr.devis.util.entityprinter;

import kr.devis.util.entityprinter.print.PrintConfigurator;
import kr.devis.util.entityprinter.print.printer.EntityPrinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        EntityPrinter printer = new EntityPrinter();
        List<Coordinate> coordinates = Arrays.asList(
                new Coordinate(1.47231323823, 1.213783243),
                new Coordinate(1.6589432432, 1.112393024),
                new Coordinate(1.1231231231, 1.123123123),
                new Coordinate(1.73494331, 1.992137932)
        );
        Map<String, String> logMap = new HashMap<>();
        logMap.put("user", "{\n\t\"name\": \"devis\",\n\t\"age\": 30\n}");
        logMap.put("date", "2020-01-01");
        logMap.put("time", "12:00:00");
        Map<String, String> logMap2 = new HashMap<>();
        logMap2.put("user", "{\n\t\"name\": \"devis\",\n\t\"age\": 30\n}");
        logMap2.put("date", "2020-01-01");
        logMap2.put("time", "12:00:00");

        List<Map<String, String>> mapList = new ArrayList<>();
        mapList.add(logMap);
        mapList.add(logMap2);

        PrintConfigurator configurator = PrintConfigurator.create();
        System.out.println(printer.drawCollection(mapList, configurator, Map.class));

    }
}
