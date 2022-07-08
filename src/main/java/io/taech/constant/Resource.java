package io.taech.constant;

import java.util.StringJoiner;

public class Resource {

    private Resource() {}

    public static final String APEX = "+";
    public static final String WALL = "|";
    public static final String LINEFEED = "\n";
    public static final String BRICK = "-";
    public static final String IGNORE_LETTER = "(\\n|\\r|\\t)";
    public static final Integer DEFAULT_MAX_LENGTH = 30;
    public static final Integer EACH_SPACE_LENGTH = 2;

    public static String join(final String... strings) {
        final StringBuilder builder = new StringBuilder();

        for(String str : strings)
            builder.append(str);

        return builder.toString();
    }
}
