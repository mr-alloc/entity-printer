package kr.devis.util.entityprinter.print.printer;

import kr.devis.util.entityprinter.print.PrintConfigurator;

import java.util.Collection;
import java.util.function.Supplier;

public class EntityPrinter implements IEntityPrinter {

    private final IEntityPrinter concretePrinter;

    public EntityPrinter() {
        this.concretePrinter = new ConcreteEntityPrinter();
    }

    @Override
    public <T> String drawEntity(T entity) {
        return protect(() -> concretePrinter.drawEntity(entity));
    }

    @Override
    public <I, T> String drawEntity(T entity, PrintConfigurator<I> configurator) {
        return protect(() -> concretePrinter.drawEntity(entity, configurator));
    }

    @Override
    public <T> String drawList(Collection<? extends T> entities, Class<? extends T> clazz) {
        return protect(() -> concretePrinter.drawList(entities, clazz));
    }

    @Override
    public <I, T> String drawList(Collection<? extends T> entities, PrintConfigurator<I> configured, Class<? extends T> clazz) {
        return protect(() -> concretePrinter.drawList(entities, configured, clazz));
    }

    private String protect(Supplier<String> supplier) {
        return protectInternalError(supplier);
    }

    private String protectInternalError(Supplier<String> supplier) {
        try {
            return supplier.get();
        } catch (Exception ex) {
            ex.printStackTrace();
            return String.format("Error occurred while printing entity. (Error: %s)", ex.getMessage());
        }
    }
}
