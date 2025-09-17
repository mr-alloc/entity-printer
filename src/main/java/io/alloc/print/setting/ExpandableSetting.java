package io.alloc.print.setting;


public interface ExpandableSetting {

    static ExpandableEntitySetting entity() {
        return new ExpandableEntitySetting();
    }

    static ExpandableMapSetting map() {
        return new ExpandableMapSetting();
    }
}
