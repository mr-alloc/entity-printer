package io.taech.print;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Wrapper {

    LONG("Long", Long.class),
    INTEGER("Integer", Integer.class),
    SHORT("Short", Short.class),
    BYTE("Byte", Byte.class),
    DOUBLE("Double", Double.class),
    FLOAT("Float", Float.class),
    STRING("String", String.class),
    CHARACTER("Character", Character.class),
    LOCAL_DATETIME("LocalDateTime", LocalDateTime.class),
    LOCAL_DATE("LocalDate", LocalDate.class),
    ;

    private String className;
    private Class clazz;

    private static final Map<String, Class> wrapperMap = Arrays.stream(values()).collect(Collectors.toMap(Wrapper::className, Wrapper::clazz));

    Wrapper (final String className,final Class clazz) {
        this.className = className;
        this.clazz = clazz;
    }

    public String className() {
        return this.className;
    }

    public Class clazz() {
        return this.clazz;
    }

    public static boolean has(String className) {
        return wrapperMap.containsKey(className);
    }

}
