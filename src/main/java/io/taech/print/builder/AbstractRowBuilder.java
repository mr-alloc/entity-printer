package io.taech.print.builder;

import io.taech.constant.Resource;
import io.taech.print.Column;
import io.taech.print.PrintConfigurator;
import io.taech.print.PrintOptionAware;
import io.taech.print.field.manager.PrintableFieldManager;
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

    protected PrintOptionAware optionAware;
    private PrintConfigurator configurator;

    protected void initialize() {
        this.floor = null;
        this.room = null;

        if (optionAware.isExceptColumn()) {
            this.getCurrentFieldManager().activatePrintableFields(this.configurator.getActivateIndexes());
        }

        if(optionAware.hasDateTimeFormat())
            this.optionAware.setDateFormatter(this.configurator.getDateTimeFormatter());

        if (this.getCurrentFieldManager().getActivatedFields().length == 0)
            return;

        this.calculateColumnInfo();
        this.setRoom();
        this.setFloor();
    }

    @Override
    public RowBuilder config(final PrintConfigurator configurator) {
        if(CommonUtils.isNull(configurator))
            return this;

        this.optionAware = new PrintOptionAware(configurator.getOptions());
        this.configurator = configurator;

        return this;
    }

    protected final String getStringValue(Object value, final Column column) {
        if (value == null) value = "(null)";

        String strValue = typeControl(value).replaceAll(IGNORE_LETTER, " ");
        Integer lengthOfValue = strValue.length();

        if (lengthOfValue > DEFAULT_MAX_LENGTH) {
            strValue = String.format("%s" + TRIPLE_DOT, strValue.substring(0, (DEFAULT_MAX_LENGTH - TRIPLE_DOT.length())));
            lengthOfValue = DEFAULT_MAX_LENGTH;
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

    abstract <I, F> PrintableFieldManager<I, F> getCurrentFieldManager();
    abstract void calculateColumnInfo();


    @Override
    public String build() {
        if(this.getCurrentFieldManager().getActivatedFields().length == 0)
            return builder.append(NO_ACTIVATED_MESSAGE).append(Resource.LINEFEED).toString();

        String[] columnNames = this.columns.stream()
                .map(Column::nameWithType)
                .toArray(String[]::new);

        // 컬럼명 세팅
        this.builder
                .append(join(LINEFEED, this.floor))
                .append(String.format(this.room, columnNames))
                .append(this.floor);

        // 컬럼 값 세팅
        for (Map<String, String> columnMap : this.columnMapList) {
            String[] columnValues = this.columns.stream()
                    .map(column -> columnMap.get(column.getName()))
                    .toArray(String[]::new);

            this.builder.append(String.format(this.room, columnValues)).append(this.floor);
        }

        if (columnMapList.isEmpty())
            this.builder.append(emptyFloor()).append(this.floor);


        return this.builder.toString();
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

    /**
     * 데이터(컬럼 명 또는 값)(이)가 들어갈 Room 세팅
     */
    private void setRoom() {
        final StringBuilder subBuilder = new StringBuilder().append(SIDE_WALL);
        for (int i = 0;i < this.columns.size();i++) {
            Integer len = this.columns.get(i).getLength() - 1;
            if (i == 0) {
                subBuilder.append(String.format(" %%-%ds", len));
                continue;
            }
            subBuilder.append(String.format("%s %%-%ds", WALL, len));
        }

        this.room = subBuilder.append(SIDE_WALL).append(LINEFEED).toString();
    }

    /**
     * +--+ 같은 층 정보 세팅
     */
    private void setFloor() {
        final StringBuilder subBuilder = new StringBuilder().append(APEX);
        for (Column col : this.columns) {
            for(int i = 0;i < col.getLength();i++) {
                subBuilder.append(BRICK);
            }
            subBuilder.append(APEX);
        }

        this.floor = subBuilder.append(LINEFEED).toString();
    }
}
