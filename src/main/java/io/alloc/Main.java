package io.alloc;

import io.alloc.constant.PrintOption;
import io.alloc.print.PrintConfigurator;
import io.alloc.print.printer.EntityPrinter;
import io.alloc.print.setting.ExpandableEntitySetting;
import io.alloc.print.setting.ExpandableSetting;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.alloc.constant.PrintOption.*;

public class Main {

    public static void main(String[] args) {
        EntityPrinter printer = new EntityPrinter();

        List<Person> blackpink = new ArrayList<>();
        blackpink.add(new Person("Jennie", "jennie@black.pink", LocalDateTime.of(1996, 1, 16, 12, 20)));
        blackpink.add(new Person("Jisoo", "jisoo@black.pink", LocalDateTime.of(1995, 1, 3, 17, 53)));
        blackpink.add(new Person("Rosé", "roseanne@black.pink", LocalDateTime.of(1997, 2, 11, 7, 12)));
        blackpink.add(new Person("Lisa", "risa@black.pink", LocalDateTime.of(1997, 3, 27, 2, 9)));

        PrintConfigurator configurator = ExpandableSetting.entity()
                .activateFields()
                .getConfig();

        System.out.println(printer.drawCollection(blackpink, configurator, Person.class));
    }

    public static class Person {
        String name;
        String email;
        LocalDateTime birthday;
        String jsonCache;

        public Person(String name, String email, LocalDateTime birthday) {
            this.name = name;
            this.email = email;
            this.birthday = birthday;
            this.jsonCache = "{\n" +
                    "\t\"name\": \"" + this.name + "\",\n" +
                    "\t\"email\": \"" + this.email + "\",\n" +
                    "\t\"birthday\": \"" + this.birthday + "\"\n" +
                    "}";

        }
    }
}
