package io.taech;

import io.taech.print.impl.Printer;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Printer out = new Printer();
        List<Member> members = new ArrayList<>();
        members.add(new Member(1L, 1L, "taechnique", "taech", "1994-09-23", "N", LocalDateTime.now()));
        members.add(new Member(2L, 2L, "홍길동", "Mr. Hong", "1997-04-17", "N", LocalDateTime.now()));
        members.add(new Member(3L, 3L, "김좌진의 아들 김두한이다.", "General Kim", "1990-11-02", "N", LocalDateTime.now()));
        members.add(new Member(3L, 3L, "김무친", "Mu7", "2000-02-18", "N", LocalDateTime.now()));


        System.out.println(out.draw(members));



    }

}
