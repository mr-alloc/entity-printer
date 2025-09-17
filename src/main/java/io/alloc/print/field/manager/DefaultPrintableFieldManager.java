package io.alloc.print.field.manager;

import io.alloc.util.CommonUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class DefaultPrintableFieldManager implements PrintableFieldManager<Field> {

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
    public void activatePrintableFields(final Set<String> fieldNames) {
        this.fields = Arrays.stream(this.fields).filter(field -> fieldNames.contains(field.getName()))
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
