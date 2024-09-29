package com.herculanoleo.spring.reactive.me.configuration;

import com.querydsl.sql.SQLTemplates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfobipQueryDSLConfiguration {

    @Bean
    public SQLTemplates sqlTemplates() {
        return SQLTemplates.DEFAULT;
    }

}
