package kr.devis.constant;

public class Resource {

    private Resource() {}

    public static final String APEX = "+";
    public static final String INNER_WALL = " ";
    public static final String SIDE_WALL = "|";
    public static final String LINEFEED = "\n";
    public static final String BRICK = "-";
    public static final String NULL_VALUE = "(null)";

    public static final byte APEX_BYTE = 43;
    public static final byte WALL_BYTE = 32;
    public static final byte SIDE_WALL_BYTE = 124;
    public static final byte LINEFEED_BYTE = 10;
    public static final byte BRICK_BYTE = 45;
    public static final byte PERCENT_BYTE = 37;
    public static final byte LETTER_S_BYTE = 115;
    public static final byte LETTER_D_BYTE = 100;
    public static final byte SPACE_BYTE = 32;

    public static final String IGNORE_LETTER = "(\\n|\\t)";
    public static final String ELLIPSIS = "...";
    public static final Integer DEFAULT_MAX_LENGTH_PER_LINE = 30;
    public static final Integer EACH_SPACE_LENGTH = 2;

    public static String join(final String... strings) {
        final StringBuilder builder = new StringBuilder();

        for (String str : strings)
            builder.append(str);

        return builder.toString();
    }
}
