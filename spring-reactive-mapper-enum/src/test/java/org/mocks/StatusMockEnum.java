package org.mocks;

import com.herculanoleo.spring.reactive.me.models.enums.MapperEnum;

public enum StatusMockEnum implements MapperEnum {
    ACTIVE("A"), INACTIVE("I");

    private final String value;

    StatusMockEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
