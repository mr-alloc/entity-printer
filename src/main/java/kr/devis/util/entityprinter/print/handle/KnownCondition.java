package kr.devis.util.entityprinter.print.handle;

public interface KnownCondition {
    String USE_COLLECTION_DRAW_METHOD = "Use draw(Collection<?> entities, PrintConfigurator<I> configurator, Class<?> clazz) method";
    String NO_ACTIVATED_MESSAGE = "No activated field. Please check optional method called by \"PrintConfigurator.activateFields()\".";
}
