package io.taech.constant;

import java.util.StringJoiner;

public class Resource {

    private Resource() {}

    public static final String APEX = "+";
    public static final String WALL = "|";
    public static final String LINEFEED = "\n";
    public static final String BRICK = "-";
    public static final String KOREAN_REGEXP = "[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]";

    public static String join(final String... strings) {
        final StringBuilder builder = new StringBuilder();

        for(String str : strings)
            builder.append(str);

        return builder.toString();
    }
}
