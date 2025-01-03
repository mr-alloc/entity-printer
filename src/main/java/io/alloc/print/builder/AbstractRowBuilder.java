package io.alloc.print.builder;

import io.alloc.constant.Resource;
import io.alloc.print.Column;
import io.alloc.print.ColumnValue;
import io.alloc.print.PrintConfigurator;
import io.alloc.print.PrintOptionAware;
import io.alloc.print.field.manager.PrintableFieldManager;
import io.alloc.print.floor.DefaultFloorGenerator;
import io.alloc.print.floor.FloorGenerator;
import io.alloc.print.floor.SuiteFloor;
import io.alloc.util.CommonUtils;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.alloc.print.handle.KnownCondition.NO_ACTIVATED_MESSAGE;

/**
 * 공통적으로 Row를 만들기위한 추상클래스
 *
 * @param <I> 인덱스 타입
 */
public abstract class AbstractRowBuilder<I> implements RowBuilder<I> {

    private static final String EMPTY_MESSAGE = "empty";

    /**
     * 실제로 쌓일 문자열을 담는 StringBuilder
     */
    protected final StringBuilder builder = new StringBuilder(Resource.LINEFEED);
    protected final List<Column> columns = new ArrayList<>();
    /**
     * 컬럼의 정보를 담는 리스트
     */
    protected final List<Map<String, String>> columnMapList = new ArrayList<>();

    private final FloorGenerator floorGenerator = new DefaultFloorGenerator();
    protected PrintOptionAware optionAware;
    private PrintConfigurator<I> configurator;

    protected abstract <F> PrintableFieldManager<I, F> getCurrentFieldManager();

    protected abstract void calculateColumnInfo();

    protected PrintConfigurator<I> getConfigurator() {
        return this.configurator;
    }


    protected void initialize() {

        if (optionAware.isExceptColumn()) {
            List<I> activateIndices = this.configurator.getActivateIndexes();
            this.getCurrentFieldManager().activatePrintableFields(activateIndices);
        }

        if (optionAware.hasDateTimeFormat())
            this.optionAware.setDateFormatter(this.configurator.getDateTimeFormatter());

        if (this.getCurrentFieldManager().hasNoActivateFields())
            return;

        // 컬럼 정보 세팅
        this.calculateColumnInfo();
        floorGenerator.generateSuiteFloor(this.columns, this.columnMapList.size());
    }

    @Override
    public RowBuilder<I> config(final PrintConfigurator<I> configurator) {
        if (CommonUtils.isNull(configurator))
            return this;

        this.optionAware = new PrintOptionAware(configurator.getOptions());
        this.configurator = configurator;

        return this;
    }

    protected final String getStringValue(Object value, final Column column) {
        if (value == null) value = Resource.NULL_VALUE;
        String typeValue = typeControl(value);

        //이스케이프 문자 그대로 출력
        String strValue = CommonUtils.escapeWhiteSpace(typeValue);
        //라인피드로 나눠서 라인개수를 확인하기때문에 replace 되지 않은 값으로 생성.
        ColumnValue columnValue = new ColumnValue(strValue);
        int lengthOfValue = strValue.length();

        if (optionAware.isNoEscape() && !optionAware.isAllowMultiline()) {
            strValue = typeValue.split(Resource.LINEFEED)[0];
            lengthOfValue = strValue.length();
        }

        if (optionAware.isAllowMultiline()) {
            String[] lines = optionAware.isNoEscape()
                    ? CommonUtils.separateWithLineFeed(typeValue.replace("\t", "  "))
                    : CommonUtils.separateWithSize(strValue, Resource.DEFAULT_MAX_LENGTH_PER_LINE);
            columnValue.applyMultiline(lines);
            lengthOfValue = columnValue.getLineLength();
            if (optionAware.isNoEscape()) {
                strValue = String.join(Resource.LINEFEED, lines);
            }
        }

        //줄임이 아니면서 최대길이를 넘어가면 줄임표를 붙여준다.
        if (!optionAware.isNoEllipsis() && !optionAware.isAllowMultiline() && lengthOfValue > Resource.DEFAULT_MAX_LENGTH_PER_LINE) {
            strValue = columnValue.getFirstLine().substring(0, (Resource.DEFAULT_MAX_LENGTH_PER_LINE - Resource.ELLIPSIS.length())) + Resource.ELLIPSIS;
            lengthOfValue = Resource.DEFAULT_MAX_LENGTH_PER_LINE;
        }

        int maxLength = Math.max(column.getLength(), (lengthOfValue + Resource.EACH_SPACE_LENGTH));
        int maxLine = Math.max(column.getLine(), columnValue.getLineCount());
        column.with(maxLength, maxLine);

        return strValue;
    }

    private String typeControl(Object value) {
        Object result;
        try {
            if (value instanceof ChronoLocalDate)
                result = ((ChronoLocalDate) value).format(optionAware.getDateFormatter());
            else if (value instanceof ChronoLocalDateTime)
                result = ((ChronoLocalDateTime) value).format(optionAware.getDateFormatter());
            else
                result = value.toString();
        } catch (UnsupportedTemporalTypeException temporalTypeException) {
            result = value.toString();
        }
        return result.toString();
    }

    @Override
    public String build() {
        if (this.getCurrentFieldManager().hasNoActivateFields())
            return builder.append(NO_ACTIVATED_MESSAGE).append(Resource.LINEFEED).toString();

        String[] columnNames = this.columns.stream()
                .map(Column::nameWithType)
                .toArray(String[]::new);

        // 컬럼명 세팅
        SuiteFloor suiteFloor = this.floorGenerator.getSuiteFloor();
        this.builder.append(suiteFloor.getFloorWithNames(columnNames));

        this.setColumnValues();

        if (columnMapList.isEmpty())
            this.builder.append(emptyFloor());

        return this.builder.toString();
    }

    private void setColumnValues() {
        SuiteFloor suiteFloor = this.floorGenerator.getSuiteFloor();
        // 컬럼 값 세팅
        ListIterator<Map<String, String>> mapListIterator = this.columnMapList.listIterator();

        while (mapListIterator.hasNext()) {
            Map<String, String> nextRow = mapListIterator.next();
            if (optionAware.isAllowMultiline()) {
                //컬럼중 가장 긴라인을 기준으로 생성
                int largestLine = this.columns.stream().map(Column::getLine).max(Integer::compareTo).orElse(1);

                //Map<컬럼명, 라인[]>
                Map<String, String[]> multiLineRow = nextRow.entrySet().stream().collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> optionAware.isNoEscape()
                                ? CommonUtils.separateWithLineFeed(entry.getValue())
                                : CommonUtils.separateWithSize(entry.getValue(), Resource.DEFAULT_MAX_LENGTH_PER_LINE)
                ));


                IntStream.range(0, largestLine).forEach(lineNumber -> {
                    String[] columnValues = columns.stream().map(col -> {
                        String[] values = multiLineRow.get(col.getName());
                        //컬럼 내 라인의 값 없다면 ""
                        return values.length > lineNumber ? values[lineNumber] : "";
                    }).toArray(String[]::new);
                    boolean skippable = Arrays.stream(columnValues).allMatch(String::isEmpty);
                    if (skippable) {
                        return;
                    }

                    this.builder.append(suiteFloor.getRoomWithValues(columnValues));
                });
            } else {
                String[] columnValues = CommonUtils.columnValuesOf(this.columns, col -> nextRow.get(col.getName()));
                this.builder.append(suiteFloor.getRoomWithValues(columnValues));
            }

            if (optionAware.isWithoutFloor() && (mapListIterator.hasNext())) {
                continue;
            }
            this.builder.append(suiteFloor.getFloorString());
        }
    }

    /**
     * @return 빈데이터를 위한 Empty Floor
     */
    private String emptyFloor() {
        SuiteFloor suiteFloor = floorGenerator.getSuiteFloor();
        String floor = suiteFloor.getFloor().toString();

        int floorLength = floor.length() - 4 <= 0
                ? 6
                : floor.length() - 4;
        final String form = new StringBuilder()
                .append(Resource.SIDE_WALL).append(" %-").append(floorLength).append("s ").append(Resource.SIDE_WALL).append(Resource.LINEFEED)
                .append(floor).append(Resource.LINEFEED)
                .toString();

        return String.format(form, EMPTY_MESSAGE);
    }
}
