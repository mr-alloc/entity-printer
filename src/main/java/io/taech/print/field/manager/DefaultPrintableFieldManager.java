package io.taech.print.field.manager;

import io.taech.print.Wrapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;

public class DefaultPrintableFieldManager implements PrintableFieldManager<Integer, Field> {

    private Class<?> typeClass;
    private Field [] fields;
    private Predicate<Field> defaultCondition = (field) ->
            (field.getType().isEnum() || field.getType().isPrimitive() || Wrapper.has(field.getType().getSimpleName()));


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
                .map(i -> this.fields[i -1])
                .filter(defaultCondition)
                .toArray(Field[]::new);
    }

    @Override
    public boolean hasNoActivateFields() {
        return this.fields.length == 0;
    }
}
