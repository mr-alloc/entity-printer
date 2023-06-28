package io.taech;

import io.taech.print.EntityPrinter;
import io.taech.print.PrintConfigurator;
import io.taech.print.impl.VerifyType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
                .excludeDataType()
                .activateFields("GROUP_TYPE", "SEND_TIME")
                .withoutFloor()
                .dateformat(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

        final String result = EntityPrinter.draw(historyMapList, HashMap.class, configurator);
        System.out.println(result);
//        ByteBuffer buf = ByteBuffer.allocate(10);
//        System.out.println("position[" + buf.position() +"] Limit["+ buf.limit()+"] Capacity["+buf.capacity()+"]" );
//
//        buf.mark(); //나중에 찾아 오기 위해 현재 위치를 지정해 둔다. (현재 위치는 0 )
//        //순차적으로 데이터 넣기 -> 데이터를 넣을 때 마다 position이 바뀐다.
//
//        buf.put((byte)10);
//        System.out.println("put result -> position[" + buf.position() +"]");
//
//        buf.put((byte)11);
//        System.out.println("put result -> position[" + buf.position() +"]");
//
//        buf.put((byte)12);
//        System.out.println("put result -> position[" + buf.position() +"]");
//
//        //mark 해 두었던 위치로 이동
//        buf.reset();
//        System.out.println("put reset -> position[" + buf.position() +"]\n");
//
//        System.out.println("데이터 들어간 결과 확인");
//
//        //데이터를 get 할 때 마다 position이 바뀐다.
//
//        for( int i = 0 ; i < 5 ; i++ ) {
//            System.out.println("position[" + buf.position() +"] value["+ buf.get() + "]\n");
//        }
//        //지정한 위치에 데이터에 넣기
//
//        buf.put(2, (byte)22);
//        buf.put(3, (byte)23);
//        buf.put(4, (byte)24);
//        System.out.println("데이터 들어간 결과 확인");
//
//        for( int i = 0 ; i < 5 ; i++ ) {
//            System.out.println("position[" + i +"] value["+ buf.get(i) + "]");
//        }
//
//        ByteBuffer allocate = ByteBuffer.allocate(10);
//        new String(allocate.array(), Charset.defaultCharset());
    }

}
