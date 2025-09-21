package com.georgiiHadzhiev.components;

import java.util.Objects;
import java.util.function.Consumer;

public class AbstractPayloadFactory {

    protected  <T> void setIfChanged(T oldValue, T newValue, Consumer<T> setter) {
        if (!Objects.equals(oldValue, newValue)) {
            setter.accept(newValue);
        }
    }
}
