package io.taech;

import java.lang.reflect.Field;
import java.util.List;

public interface PrintableFieldManager {

    void activatePrintableFields(List<Integer> fieldIndexes);

    Field [] getActivatedFields();

    Class<?> getTypeClass();
}
