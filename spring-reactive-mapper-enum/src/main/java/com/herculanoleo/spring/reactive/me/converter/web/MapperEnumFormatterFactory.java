package com.herculanoleo.spring.reactive.me.converter.web;

import com.herculanoleo.spring.reactive.me.models.enums.MapperEnum;
import org.springframework.format.Formatter;
import org.springframework.lang.NonNull;

public class MapperEnumFormatterFactory {
    public Formatter<? extends MapperEnum> getFormatter(@NonNull final Class<? extends MapperEnum> targetType) {
        return new MapperEnumFormatter<>(targetType);
    }
}
