package io.taech.print.builder;

import io.taech.constant.Resource;
import io.taech.print.Column;
import io.taech.print.ColumnValue;
import io.taech.print.PrintConfigurator;
import io.taech.print.PrintOptionAware;
import io.taech.print.field.manager.PrintableFieldManager;
import io.taech.print.floor.DefaultFloorGenerator;
import io.taech.print.floor.FloorGenerator;
import io.taech.print.floor.SuiteFloor;
import io.taech.util.CommonUtils;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.taech.constant.Resource.*;

public abstract class AbstractRowBuilder<I> implements RowBuilder<I> {


    protected final StringBuilder builder = new StringBuilder();
    protected final List<Column> columns = new ArrayList<>();
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
        this.floor = null;
        this.room = null;

        if (optionAware.isExceptColumn()) {
            List<I> activateIS = this.configurator.getActivateIndexes();
            this.getCurrentFieldManager().activatePrintableFields(activateIS);
        }

        if(optionAware.hasDateTimeFormat())
            this.optionAware.setDateFormatter(this.configurator.getDateTimeFormatter());

        if (this.getCurrentFieldManager().hasNoActivateFields())
            return;

        // 컬럼 정보 세팅
        this.calculateColumnInfo();
        floorGenerator.generateSuiteFloor(this.columns);
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
        if (value == null) value = NULL_VALUE;

        String strValue = typeControl(value).replaceAll(IGNORE_LETTER, " ");
        ColumnValue columnValue = new ColumnValue(typeControl(value));
        Integer lengthOfValue = columnValue.getLineLength();

        if (optionAware.isAllowMultiline()) {

        } else {

            if (lengthOfValue > DEFAULT_MAX_LENGTH_PER_LINE) {
                strValue = columnValue.getFirstLine().substring(0, (DEFAULT_MAX_LENGTH_PER_LINE - ELLIPSIS.length())) + ELLIPSIS;
                lengthOfValue = DEFAULT_MAX_LENGTH_PER_LINE;
            }

        }

        column.setLength(Math.max(column.getLength(), (lengthOfValue + EACH_SPACE_LENGTH)));

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
        for (int i = 0; i < this.columnMapList.size(); i++) {
            Map<String, String> columnMap = this.columnMapList.get(i);
            String[] columnValues = CommonUtils.columnValuesOf(this.columns, (col) -> columnMap.get(col.getName()));
            this.builder.append(suiteFloor.getRoomWithValues(columnValues));

            if (optionAware.isWithoutFloor() && (i != this.columnMapList.size() - 1)) {
                continue;
            }
            this.builder.append(suiteFloor.getFloorString());
        }
    }


    /**
     * @return 빈데이터를 위한 Empty Floor
     */
    private String emptyFloor() {
        int floorLength = this.floor.length() - 4 <= 0 ? 6 : this.floor.length() - 4;

        final String form = join(
                SIDE_WALL, " %-",
                String.valueOf(floorLength), "s",
                SIDE_WALL, LINEFEED);

        return String.format(form, EMPTY_MESSAGE);
    }
}
