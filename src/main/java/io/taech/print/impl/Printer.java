package io.taech.print.impl;

import io.taech.print.EntityPrinter;

public class Printer implements EntityPrinter {

    private EntityPrinter printer;

    public Printer() {
        this.printer = new DefaultPrinter();
    }

    @Override
    public String draw(Object obj) throws Exception {
        return printer.draw(obj);
    }
}
