package com.herculanoleo.spring.reactive.me.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PersonStatus implements MapperEnum {

    ACTIVE("A"), INACTIVE("I");

    private final String value;

}
