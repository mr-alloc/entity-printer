package io.taech;

import io.taech.print.impl.Printer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Printer out = new Printer();
        History history = new History(1L, "JOIN", "Send email for verify", "915e5f48-c836-4170-aed0-d818d88f16f7");

        System.out.println(out.draw(history));



    }

}
