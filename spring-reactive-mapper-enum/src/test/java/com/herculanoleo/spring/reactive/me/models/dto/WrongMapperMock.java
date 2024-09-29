package com.herculanoleo.spring.reactive.me.models.dto;

import com.herculanoleo.spring.reactive.me.models.enums.MapperEnum;

public class WrongMapperMock implements MapperEnum {
    private final String value;

    public WrongMapperMock(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
