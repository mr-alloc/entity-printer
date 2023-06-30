package kr.devis.entityprinter.print.field.manager;

import kr.devis.entityprinter.util.CommonUtils;

import java.lang.reflect.Field;
import java.util.List;

public class DefaultPrintableFieldManager implements PrintableFieldManager<Integer, Field> {

    private final Class<?> typeClass;
    private Field[] fields;

    public DefaultPrintableFieldManager(final Class<?> typeCLass) {
        this.typeClass = typeCLass;
        this.fields = typeCLass.getDeclaredFields();
    }

    public Class<?> getTypeClass() {
        return this.typeClass;
    }

    @Override
    public Field [] getActivatedFields() {
        return this.fields;
    }


    @Override
    public void activatePrintableFields(final List<Integer> fieldIndexes) {
        this.fields = fieldIndexes.stream()
                .filter(i -> this.fields.length >= i)
                .map(i -> this.fields[i - 1])
                .filter(field -> CommonUtils.isPrintableField(field.getType()))
                .toArray(Field[]::new);
    }

    @Override
    public boolean hasNoActivateFields() {
        return this.fields.length == 0;
    }
}
