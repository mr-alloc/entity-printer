package io.taech.print.field.manager;

import java.lang.reflect.Field;
import java.util.List;

public interface PrintableFieldManager {

    void activatePrintableFields(List<? extends Object> fieldKeys);

    Object [] getActivatedFields();

    Class<?> getTypeClass();
}
