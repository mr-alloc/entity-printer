package kr.devis.entityprinter.print.handle;

public interface KnownCondition {
    String USE_COLLECTION_DRAW_METHOD = "Use draw(Collection<?> entities, PrintConfigurator<I> configurator, Class<?> clazz) method";
    String CANNOT_USE_COLLECTION_AS_INNER_TYPE = "Not supported inner type for collection.";
}
