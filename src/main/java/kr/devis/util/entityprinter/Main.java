package kr.devis.util.entityprinter;

import kr.devis.util.entityprinter.print.EntityPrinter;
import kr.devis.util.entityprinter.print.PrintConfigurator;
import kr.devis.util.entityprinter.print.impl.VerifyType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
//
//        List<Coordinate> coordinates = new ArrayList<>();
//        coordinates.add(new Coordinate(1, 2));
//        coordinates.add(new Coordinate(2, nul));
//        coordinates.add(new Coordinate(4, 2));
//        coordinates.add(new Coordinate(4, 3));

        List<History> histories = new ArrayList<>();
        histories.add(new History(1L, "JOIN", "authenticate for join", "token-test-info"));

        List<Map<String, Object>> historyMapList = new ArrayList<>();
        Map<String, Object> historyMap = new LinkedHashMap<>();
        historyMap.put("ID", 1L);
        historyMap.put("GROUP_TYPE", "JOIN");
        historyMap.put("VERIFY_TYPE", VerifyType.EMAIL);
        historyMap.put("SEND_TIME", LocalDateTime.now());

        Map<String, Object> historyMap2 = new LinkedHashMap<>();
        historyMap2.put("ID", 2L);
        historyMap2.put("GROUP_TYPE", "FIND_PASSWORD");
        historyMap2.put("VERIFY_TYPE", null);
        historyMap.put("VERIFY_TYPE", VerifyType.PHONE);
        historyMap2.put("SEND_TIME", LocalDateTime.now());

        historyMapList.add(historyMap);
        historyMapList.add(historyMap2);

        PrintConfigurator configurator = PrintConfigurator.create()
                .withoutFloor()
                .dateformat(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        EntityPrinter printer = new EntityPrinter();

        final String result = printer.draw(historyMapList, configurator, Map.class);
        System.out.println(result);

    }

}
