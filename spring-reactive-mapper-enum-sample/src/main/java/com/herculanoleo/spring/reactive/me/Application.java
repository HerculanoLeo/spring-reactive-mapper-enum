package com.herculanoleo.spring.reactive.me;

import com.herculanoleo.spring.reactive.me.models.annotation.EnableMapperEnum;
import com.herculanoleo.spring.reactive.me.models.annotation.EnableR2DBCMapperEnum;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMapperEnum
@EnableR2DBCMapperEnum
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
