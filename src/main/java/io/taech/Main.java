package io.taech;

import io.taech.print.builder.BasicRowBuilder;
import io.taech.print.builder.RowBuilder;
import io.taech.print.impl.Printer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ArrayList<Object> list = new ArrayList<>();
        list.add(new History(1L, "JOIN", "인증 이메일 전송", "915e5f48-c836-4170-aed0-d818d88f16f7"));
        list.add(new History(2L, "CHANGE_PASSWORD", "인증 이메일 전송", "915e5f48-c836-4170-aed0-d818d88f16f7"));
        list.add(new History(3L, "JUST_BECAUSE", "인증 이메일 전송", "915e5f48-c836-4170-aed0-d818d88f16f7"));
        list.add(new History(4L, "ANY", "인증 이메일 전송", "915e5f48-c836-4170-aed0-d818d88f16f7"));
        list.add(new History(5L, "FIND", "인증 이메일 전송", "915e5f48-c836-4170-aed0-d818d88f16f7"));
        list.add(new History(6L, "LOOKING_FOR_ACCOUNT", "인증 이메일 전송", "915e5f48-c836-4170-aed0-d818d88f16f7"));
        list.add(new History(7L, "JOIN", "인증 이메일 전송", "915e5f48-c836-4170-aed0-d818d88f16f7"));


        RowBuilder rowBuilder = new BasicRowBuilder();
        rowBuilder.proceed(list).build();

        System.out.println(rowBuilder.getResult());

        Printer out = new Printer();
        System.out.println(out.draw(list));
        System.out.println(rowBuilder.getResult());

    }

}
