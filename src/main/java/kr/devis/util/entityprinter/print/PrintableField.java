package kr.devis.util.entityprinter.print;

public class PrintableField<I, F> {

    private I indexType;
    private F field;

    public F getField() {
        return this.field;
    }

    public I getIndexType() {
        return this.indexType;
    }
}
