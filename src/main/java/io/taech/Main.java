package io.taech;

import io.taech.constant.BuilderType;
import io.taech.constant.PrintOption;
import io.taech.print.EntityPrinter;
import io.taech.print.PrintConfigurator;
import io.taech.print.impl.VerifyType;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        List<Map<String, Object>> historyMapList = new ArrayList<>();
        Map<String, Object> historyMap = new HashMap<>();
        historyMap.put("ID", 1L);
        historyMap.put("GROUP_TYPE", "JOIN");
        historyMap.put("VERIFY_TYPE", VerifyType.EMAIL);
        historyMap.put("UNKNOWN_COLUMN", "UNKNOWN");
        historyMap.put("SEND_TIME", LocalDateTime.now());

        Map<String, Object> historyMap2 = new HashMap<>();
        historyMap2.put("ID", 2L);
        historyMap2.put("GROUP_TYPE", "FIND_PASSWORD");
        historyMap2.put("VERIFY_TYPE", VerifyType.PHONE);
        historyMap2.put("SEND_TIME", LocalDateTime.now());

        historyMapList.add(historyMap);
        historyMapList.add(historyMap2);


        final String result = EntityPrinter.draw(historyMapList, HashMap.class);

        System.out.println(result);

    }

}
