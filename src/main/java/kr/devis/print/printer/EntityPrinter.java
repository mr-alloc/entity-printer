package kr.devis.print.printer;

import kr.devis.print.PrintConfigurator;

import java.util.Collection;
import java.util.function.Supplier;

public final class EntityPrinter implements IEntityPrinter {

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
    public <T> String drawCollection(Collection<? extends T> entities, Class<? extends T> clazz) {
        return protect(() -> concretePrinter.drawCollection(entities, clazz));
    }

    @Override
    public <I, T> String drawCollection(Collection<? extends T> entities, PrintConfigurator<I> configured, Class<? extends T> clazz) {
        return protect(() -> concretePrinter.drawCollection(entities, configured, clazz));
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
