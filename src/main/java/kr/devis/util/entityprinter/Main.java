package kr.devis.util.entityprinter;

import kr.devis.util.entityprinter.print.PrintConfigurator;
import kr.devis.util.entityprinter.print.printer.EntityPrinter;
import kr.devis.util.entityprinter.print.setting.ExpandableSetting;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        EntityPrinter printer = new EntityPrinter();

        List<Member> members = Arrays.asList(
                new Member(1L, "painkiller", "pkiller@gmail.com", true,
                        LocalDate.of(1995, 12, 12),
                        LocalTime.of(0, 2, 12),
                        LocalDateTime.of(LocalDate.of(2022, 3, 13), LocalTime.of(20, 8, 15))
                ),
                new Member(2L, "drawer", "jdevcka12@gmail.com", true,
                        LocalDate.of(1997, 1, 2),
                        null,
                        LocalDateTime.of(LocalDate.of(2023, 9, 2), LocalTime.of(12, 3, 22))
                ),
                new Member(3L, "cobet", "coiner@kibet.com", true,
                        LocalDate.of(2001, 8, 6),
                        null,
                        LocalDateTime.of(LocalDate.of(2022, 4, 1), LocalTime.of(14, 33, 9))
                ),
                new Member(4L, "acheii", null, false,
                        LocalDate.of(1997, 8, 8),
                        LocalTime.of(7, 23, 0),
                        LocalDateTime.of(LocalDate.of(2023, 12, 4), LocalTime.of(9, 2, 2))
                )
        );

        PrintConfigurator<Integer> config = ExpandableSetting.EXPANDABLE_ENTITY_SETTING
                .excludeDataType()
                .dateformat("yyyy-MM-dd HH:mm:ss")
                .noEscape()
                .withoutFloor()
                .getConfig();
        PrintConfigurator<String> config1 = ExpandableSetting.EXPANDABLE_MAP_SETTING
                .activateFields("user", "time")
                .getConfig();
        System.out.println(printer.drawCollection(members, config, Member.class));


        Map<String, Object> logMap = new HashMap<>();
        logMap.put("user", "{\n\t\"name\": \"devis\",\n\t\"age\": 30\n}");
        logMap.put("date", "2020-01-01");
        logMap.put("time", "12:00:00");

        Map<String, Object> logMap2 = new HashMap<>();
        logMap2.put("user", "{\n\t\"name\": \"ultrarisk\",\n\t\"age\":3213\n}");
        logMap2.put("date", "2019-03-20");
        logMap2.put("time", "19:34:12");

        List<Map<String, Object>> mapList = Arrays.asList(logMap, logMap2);

        System.out.println(printer.drawCollection(mapList, config1, Map.class));

    }
}
