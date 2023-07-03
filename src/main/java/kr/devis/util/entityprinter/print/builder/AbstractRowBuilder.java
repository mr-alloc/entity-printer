package kr.devis.util.entityprinter.print.builder;

import kr.devis.util.entityprinter.constant.Resource;
import kr.devis.util.entityprinter.print.Column;
import kr.devis.util.entityprinter.print.ColumnValue;
import kr.devis.util.entityprinter.print.PrintConfigurator;
import kr.devis.util.entityprinter.print.PrintOptionAware;
import kr.devis.util.entityprinter.print.field.manager.PrintableFieldManager;
import kr.devis.util.entityprinter.print.floor.DefaultFloorGenerator;
import kr.devis.util.entityprinter.print.floor.FloorGenerator;
import kr.devis.util.entityprinter.print.floor.SuiteFloor;
import kr.devis.util.entityprinter.util.CommonUtils;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * 공통적으로 Row를 만들기위한 추상클래스
 *
 * @param <I> 인덱스 타입
 */
public abstract class AbstractRowBuilder<I> implements RowBuilder<I> {


    /**
     * 실제로 쌓일 문자열을 담는 StringBuilder
     */
    protected final StringBuilder builder = new StringBuilder();
    protected final List<Column> columns = new ArrayList<>();
    /**
     * 컬럼의 정보를 담는 리스트
     */
    protected final List<Map<String, String>> columnMapList = new ArrayList<>();
    protected String floor;
    protected String room;

    private static final String EMPTY_MESSAGE = "empty";
    private static final String NO_ACTIVATED_MESSAGE = "There are no Activated fields";

    private final FloorGenerator floorGenerator = new DefaultFloorGenerator();
    protected PrintOptionAware optionAware;
    private PrintConfigurator<I> configurator;

    protected abstract <F> PrintableFieldManager<I, F> getCurrentFieldManager();

    protected abstract void calculateColumnInfo();


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

        String strValue = typeControl(value).replaceAll(Resource.IGNORE_LETTER, " ");
        ColumnValue columnValue = new ColumnValue(typeControl(value));
        Integer lengthOfValue = columnValue.getLineLength();

        if (optionAware.isAllowMultiline()) {
            // multi line 대응 행, 열 길이 계산
        } else {

            if (lengthOfValue > Resource.DEFAULT_MAX_LENGTH_PER_LINE) {
                strValue = columnValue.getFirstLine().substring(0, (Resource.DEFAULT_MAX_LENGTH_PER_LINE - Resource.ELLIPSIS.length())) + Resource.ELLIPSIS;
                lengthOfValue = Resource.DEFAULT_MAX_LENGTH_PER_LINE;
            }

        }

        column.setLength(Math.max(column.getLength(), (lengthOfValue + Resource.EACH_SPACE_LENGTH)));

        return strValue;
    }

    private String typeControl(Object value) {
        Object result;

        if (value instanceof ChronoLocalDate)
            result = ((ChronoLocalDate) value).format(optionAware.getDateFormatter());
        else if (value instanceof ChronoLocalDateTime)
            result = ((ChronoLocalDateTime) value).format(optionAware.getDateFormatter());
        else
            result = value;

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
            this.builder.append(emptyFloor()).append(this.floor);
        return this.builder.toString();
    }

    private void setColumnValues() {
        SuiteFloor suiteFloor = this.floorGenerator.getSuiteFloor();
        if (this.optionAware.isAllowMultiline()) {
            return;
        }
        // 컬럼 값 세팅
        ListIterator<Map<String, String>> mapListIterator = this.columnMapList.listIterator();

        while (mapListIterator.hasNext()) {
            Map<String, String> next = mapListIterator.next();
            String[] columnValues = CommonUtils.columnValuesOf(this.columns, col -> next.get(col.getName()));
            this.builder.append(suiteFloor.getRoomWithValues(columnValues));

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
        int floorLength = this.floor.length() - 4 <= 0
                ? 6
                : this.floor.length() - 4;

        final String form = Resource.join(
                Resource.SIDE_WALL, " %-",
                String.valueOf(floorLength), "s",
                Resource.SIDE_WALL, Resource.LINEFEED);

        return String.format(form, EMPTY_MESSAGE);
    }
}
