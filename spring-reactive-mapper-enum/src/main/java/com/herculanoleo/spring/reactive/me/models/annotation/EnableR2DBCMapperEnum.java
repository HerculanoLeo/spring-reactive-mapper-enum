package com.herculanoleo.spring.reactive.me.models.annotation;

import com.herculanoleo.spring.reactive.me.configuration.R2DBCConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({R2DBCConfiguration.class})
public @interface EnableR2DBCMapperEnum {
}
