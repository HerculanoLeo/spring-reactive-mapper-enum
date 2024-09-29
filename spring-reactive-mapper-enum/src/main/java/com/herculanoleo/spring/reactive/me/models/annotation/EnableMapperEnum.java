package com.herculanoleo.spring.reactive.me.models.annotation;

import com.herculanoleo.spring.reactive.me.configuration.MapperResourceLoader;
import com.herculanoleo.spring.reactive.me.configuration.StartConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({StartConfiguration.class, MapperResourceLoader.class})
public @interface EnableMapperEnum {

    String[] value() default {};

    String[] basePackages() default {};

}
