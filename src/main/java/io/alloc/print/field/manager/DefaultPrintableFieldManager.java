package io.alloc.print.field.manager;

import io.alloc.util.CommonUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class DefaultPrintableFieldManager implements PrintableFieldManager<Integer, Field> {

    private final Class<?> typeClass;
    private Field[] fields;

    public DefaultPrintableFieldManager(final Class<?> typeCLass) {
        this.typeClass = typeCLass;
        this.fields = filterForPrintable(typeCLass.getDeclaredFields());
    }

    private Field[] filterForPrintable(Field[] declaredFields) {
        return Arrays.stream(declaredFields)
                .filter(fieldFilter())
                .toArray(Field[]::new);
    }

    public Class<?> getTypeClass() {
        return this.typeClass;
    }

    @Override
    public Field[] getActivatedFields() {
        return this.fields;
    }


    @Override
    public void activatePrintableFields(final List<Integer> fieldIndexes) {
        this.fields = fieldIndexes.stream()
                .filter(i -> this.fields.length >= i)
                .map(i -> this.fields[i - 1])
                .filter(fieldFilter())
                .toArray(Field[]::new);
    }

    private Predicate<Field> fieldFilter() {
        return (field ->
                CommonUtils.isPrintableField(field.getType()) &&
                        !Modifier.isStatic(field.getModifiers()));
    }

    @Override
    public boolean hasNoActivateFields() {
        return this.fields.length == 0;
    }
}
