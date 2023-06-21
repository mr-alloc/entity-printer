package io.taech.print.field.manager;

import io.taech.print.Wrapper;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PrintableMapManager<VALUE, WRAPPER> implements PrintableFieldManager<String, Map.Entry<String, Object>> {

    private Class<WRAPPER> typeClass;
    private Map<String, VALUE> fieldMap;
    private final Predicate<VALUE> defaultCondition = value -> {
        Class<?> valueType = value.getClass();

        return (valueType.isEnum() || valueType.isPrimitive() || Wrapper.has(valueType.getSimpleName()));
    };

    public PrintableMapManager(final Class<WRAPPER> typeClass, final Map<String, VALUE> fieldMap) {
        this.typeClass = typeClass;
        this.fieldMap = fieldMap;
    }


    @Override
    public void activatePrintableFields(List<String> fieldIndexes) {
        this.fieldMap = fieldIndexes.stream()
                .filter(key -> this.fieldMap.containsKey(key) && defaultCondition.test(this.fieldMap.get(key)))
                .map(key -> new AbstractMap.SimpleEntry<>(key, this.fieldMap.get(key)))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    @Override
    public Map.Entry<String, Object>[] getActivatedFields() {
        return fieldMap.entrySet().toArray(new Map.Entry[0]);
    }

    @Override
    public Class<?> getTypeClass() {
        return this.typeClass;
    }

    @Override
    public boolean hasNoActivateFields() {
        return this.fieldMap.isEmpty();
    }
}
