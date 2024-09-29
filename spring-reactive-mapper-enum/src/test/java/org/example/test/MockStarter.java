package org.example.test;

import com.herculanoleo.spring.reactive.me.models.annotation.EnableMapperEnum;

@EnableMapperEnum(
        value = {"com.herculanoleo.spring"},
        basePackages = {"org.example.test.enums"}
)
public class MockStarter {
}
