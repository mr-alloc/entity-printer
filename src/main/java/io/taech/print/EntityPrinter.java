package io.taech.print;

import io.taech.constant.BuilderType;
import io.taech.util.CommonUtils;

public class EntityPrinter {

    public static String draw(final Object obj, final Class<?> typeCLass) {
        return draw(obj, typeCLass, null);
    }
    public static String draw(final Object obj, final Class<?> typeClass, PrintConfigurator configurator) {
        if(CommonUtils.isNull(configurator))
            configurator = new PrintConfigurator(BuilderType.DEFAULT);

        return configurator.apply().build();
    }
}
