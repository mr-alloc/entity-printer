package io.taech.print.field.manager;

import io.taech.print.Wrapper;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PrintableMapManager<V> implements PrintableFieldManager<String, Map.Entry> {

    private Class<?> typeClass;
    private Map<String, V> fieldMap;
    private Predicate<V> defaultCondition = (v) -> {
        Class<?> valueType = v.getClass();
        return (valueType.isEnum() || valueType.isPrimitive() || Wrapper.has(valueType.getSimpleName()));
    };

    public PrintableMapManager(final Class<?> typeClass, final Map<String, V> fieldMap) {
        this.typeClass = typeClass;
        this.fieldMap = fieldMap;
    }


    @Override
    public void activatePrintableFields(List<String> fieldIndexes) {
        this.fieldMap = fieldIndexes.stream()
                .filter(key -> this.fieldMap.containsKey(key) && defaultCondition.test(this.fieldMap.get(key)))
                .map(key -> new AbstractMap.SimpleEntry<>(key, this.fieldMap.get(key)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map.Entry<String, Object>[] getActivatedFields() {
        return fieldMap.entrySet().stream().toArray(Map.Entry[]::new);
    }

    @Override
    public Class<?> getTypeClass() {
        return this.typeClass;
    }
}
