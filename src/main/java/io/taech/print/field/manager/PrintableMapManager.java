package io.taech.print.field.manager;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class PrintableMapManager implements PrintableFieldManager {

    private Class<? extends Map> typeClass;

    public PrintableMapManager() {
        this.typeClass = Map.class;
    }


    @Override
    public void activatePrintableFields(List<Integer> fieldIndexes) {

    }

    @Override
    public Field[] getActivatedFields() {
        return new Field[0];
    }

    @Override
    public Class<? extends Map> getTypeClass() {
        return this.typeClass;
    }
}
