package io.alloc.print.field.manager;

import java.util.List;
import java.util.Set;

public interface PrintableFieldManager<F> {

    void activatePrintableFields(Set<String> fieldIndexes);

    F [] getActivatedFields();

    Class<?> getTypeClass();

    boolean hasNoActivateFields();
}
