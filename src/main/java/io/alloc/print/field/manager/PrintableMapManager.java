package io.alloc.print.field.manager;

import io.alloc.util.CommonUtils;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class PrintableMapManager<V, W> implements PrintableFieldManager<Entry<String, Object>> {

    private final Class<W> typeClass;
    private Map<String, V> fieldMap;

    public PrintableMapManager(final Class<W> typeClass, final Map<String, V> fieldMap) {
        this.typeClass = typeClass;
        this.fieldMap = fieldMap;
    }


    @Override
    public void activatePrintableFields(Set<String> fieldNames) {
        this.fieldMap = fieldNames.stream()
                .filter(key -> this.fieldMap.containsKey(key) && CommonUtils.isPrintableField(this.fieldMap.get(key).getClass()))
                .map(key -> new AbstractMap.SimpleEntry<>(key, this.fieldMap.get(key)))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> a, LinkedHashMap::new));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Entry<String, Object>[] getActivatedFields() {
        return fieldMap.entrySet().toArray(new Entry[0]);
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
