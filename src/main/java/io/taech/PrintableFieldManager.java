package io.taech;

import java.lang.reflect.Field;

public interface PrintableFieldManager {

    void activatePrintableFields(Integer... fieldIndexes);

    Field [] getActivatedFields();

    Class<?> getTypeClass();
}
