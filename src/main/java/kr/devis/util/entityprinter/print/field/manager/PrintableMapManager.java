package kr.devis.util.entityprinter.print.field.manager;

import kr.devis.util.entityprinter.util.CommonUtils;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PrintableMapManager<V, W> implements PrintableFieldManager<String, Map.Entry<String, Object>> {

    private Class<W> typeClass;
    private Map<String, V> fieldMap;

    public PrintableMapManager(final Class<W> typeClass, final Map<String, V> fieldMap) {
        this.typeClass = typeClass;
        this.fieldMap = fieldMap;
    }


    @Override
    public void activatePrintableFields(List<String> fieldIndexes) {
        this.fieldMap = fieldIndexes.stream()
                .filter(key -> this.fieldMap.containsKey(key) && CommonUtils.isPrintableField(this.fieldMap.get(key).getClass()))
                .map(key -> new AbstractMap.SimpleEntry<>(key, this.fieldMap.get(key)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
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
