package io.taech;

import io.taech.print.Wrapper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Predicate;

public class DefaultPrintableFieldManager implements PrintableFieldManager{

    private Class<?> typeCLass;
    private Field [] fields;
    private Predicate<Field> defaultCondition = (field) ->
            (field.getType().isEnum() || field.getType().isPrimitive() || Wrapper.has(field.getType().getSimpleName()));


    public DefaultPrintableFieldManager(final Class<?> typeCLass) {
        this.typeCLass = typeCLass;
        this.fields = typeCLass.getDeclaredFields();
    }

    public Class<?> getTypeClass() {
        return this.typeCLass;
    }
    @Override
    public Field [] getActivatedFields() {
        return this.fields;
    }


    @Override
    public void activatePrintableFields(final Integer... fieldIndexes) {

        this.fields = Arrays.stream(fieldIndexes).distinct().sorted()
                .filter(i -> this.fields.length >= i).map(i -> this.fields[i]).toArray(Field[]::new);
    }
}
