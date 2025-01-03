package io.taech;

import io.taech.print.impl.Printer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Printer out = new Printer();
        ArrayList<History> list = new ArrayList<>();
        History history = new History(1L, "JOIN", "인증 이메일 전송", "915e5f48-c836-4170-aed0-d818d88f16f7");
        History history2 = new History(2L, "CHANGE", "in mai sn",  "35432348-cv36-4170-aed0-d818d82121312");

        list.add(history);
        list.add(history2);
        System.out.println(out.draw(list));



    }

}
